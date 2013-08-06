package com.crowdriff.styx;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerformanceTest {

    private final Logger logger = LoggerFactory.getLogger(PerformanceTest.class);
    StyxQueue q = null;

    public PerformanceTest(StyxQueue q) {
        this.q = q;
    }

    public int doRun() {
        int numThreads = 1;
        ExecutorService pool = Executors.newFixedThreadPool(numThreads);
        List<Worker> workers = Lists.newArrayList();
        for(int i = 0; i < numThreads; i++) {
            Worker w = new Worker(q);
            workers.add(w);
            pool.submit(w);
        }

        try {
            Thread.sleep(10000);
        }
        catch(Exception e) {}

        int size = q.size();
        for(Worker w : workers)
            w.kill();
        pool.shutdownNow();
        logger.info("Wrote {} items", String.valueOf(size));
        return size;
    }

    private class Worker implements Runnable {
        private final StyxQueue q;
        private boolean flag = true;
        private ImmutableList<String> uuids = ImmutableList.of(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());

        public Worker(StyxQueue q) {
            this.q = q;
        }

        @Override
        public void run() {
            while(flag) {
                try {
                    for(String uid : uuids)
                        q.put(uid);
                }
                catch(Exception e) {
                    e.printStackTrace();
                    logger.error("Couldn't write message", e);
                }
            }
        }

        public void kill() {
            this.flag = false;
        }
    }
}
