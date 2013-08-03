package com.crowdriff.styx;

import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public enum Storage {

	I;

	private static final Logger logger = LoggerFactory.getLogger(Storage.class);
	private final ConcurrentLinkedQueue<StyxMessage> queue = Queues.newConcurrentLinkedQueue();
	private final AtomicInteger size = new AtomicInteger(0);

	private Storage() {}

	@Nullable
	public StyxMessage get() {
		if(queue.isEmpty()) {
			logger.info("Queue is empty, returning null");
			return null;
		}
		else {
			size.decrementAndGet();
			return queue.poll();
		}
	}

	public void put(StyxMessage message) {
		queue.offer(message);
		size.incrementAndGet();
	}

	public int size() {
		return size.get();
	}

}
