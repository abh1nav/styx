package com.crowdriff.styx;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public enum Redis {
    I;

    private List<Jedis> all;
    private ConcurrentLinkedQueue<Jedis> read, write;

    /**
     * Initialize the connections by passing in a list of hosts.
     * @param hosts List of Redis hosts as host or host:port
     */
    protected void init(String[] hosts) {
        all = Lists.newArrayList();
        read = Queues.newConcurrentLinkedQueue();
        write = Queues.newConcurrentLinkedQueue();
        for(String host : hosts) {
            Jedis jedis = connect(host);
            all.add(jedis);
            read.offer(jedis);
            write.offer(jedis);
        }
    }

    /**
     * Convenience method to initialize the connection using a single host.
     * @param host host or host:port
     */
    protected void init(String host) {
        init(new String[] { host });
    }


    /**
     * Retrieve a the next connection in line for reading.
     */
    @Nullable
    protected synchronized Jedis getReader() {
        Jedis j = read.poll();
        if(null != j) {
            read.offer(j);
        }
        return j;
    }

    /**
     * Retrieve a the next connection in line for writing.
     */
    @Nullable
    protected synchronized Jedis getWriter() {
        Jedis j = write.poll();
        if(null != j) {
            write.offer(j);
        }
        return j;
    }

    /**
     * Return a list of all active connections in the rotation.
     * This is needed to perform some commands like Styx#deleteQueue or StyxQueue#size
     */
    protected List<Jedis> getAll() {
        return all;
    }

    /**
     * Remove the given connection from the rotation.
     */
    protected synchronized void remove(Jedis jedis) {
        Preconditions.checkNotNull(jedis);
        if(all.contains(jedis)) {
            all.remove(jedis);
            read.remove(jedis);
            write.remove(jedis);
        }
    }

    /**
     * Return the number of connections in play at the moment.
     */
    protected int size() {
        return all.size();
    }

    /************************************************************************************
     * Helpers
     ************************************************************************************/
    /**
     * Parse a host or host:port connection string.
     * @param host
     * @return
     */
    private Jedis connect(String host) {
        if(host.contains(":")) {
            String[] h = host.split(":");
            return new Jedis(h[0], Integer.parseInt(h[1]));
        }
        else {
            return new Jedis(host);
        }
    }
}
