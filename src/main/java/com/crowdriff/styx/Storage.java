package com.crowdriff.styx;

import com.google.common.collect.Queues;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public enum Storage {

	I;

	private final ConcurrentLinkedQueue<StyxMessage> queue = Queues.newConcurrentLinkedQueue();
	private final AtomicInteger size = new AtomicInteger(0);

	private Storage() {}

	@Nullable
	public StyxMessage get() {
		if(queue.isEmpty()) {
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


}
