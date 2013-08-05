package com.crowdriff.styx;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentLinkedQueue;

public enum Redis {
    I;

    private ConcurrentLinkedQueue<Jedis> connections = Queues.newConcurrentLinkedQueue();

    /**
     * Initialize the connections by passing in a list of hosts.
     * @param hosts List of Redis hosts as host or host:port
     */
    public void init(String[] hosts) {
        connections = Queues.newConcurrentLinkedQueue();
        for(String host : hosts) {
            connections.offer(connect(host));
        }
    }

    /**
     * Initialize the connection by passing in a single host.
     * @param host host or host:port
     */
    public void init(String host) {
        connections = Queues.newConcurrentLinkedQueue();
        connections.offer(connect(host));
    }


    /**
     * Retrieve a the next connection in line.
     */
    @Nullable
    public synchronized Jedis get() {
        Jedis j = connections.poll();
        if(null != j) {
            connections.offer(j);
        }
        return j;
    }

    /**
     * Remove the given connection from the rotation.
     */
    public synchronized void remove(Jedis jedis) {
        Preconditions.checkNotNull(jedis);
        if(connections.contains(jedis)) {
            connections.remove(jedis);
        }
    }

    /**
     * Return the number of connections in play at the moment.
     * Warning: This is a O(n) method because of the underlying ConcurrentLinkedQueue#size implementation.
     */
    public int size() {
        return connections.size();
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
