package com.crowdriff.styx;

import com.google.common.annotations.VisibleForTesting;
import redis.clients.jedis.Jedis;

public class Styx {

    private Redis connection;

    public Styx(String[] redisHosts) {
        connection = new Redis(redisHosts);
    }

    public StyxQueue getQueue(String queueName) {
        return new StyxQueue(connection, queueName);
    }

    public void deleteQueue(StyxQueue queue) {
        for(Jedis jedis : connection.getAll()) {
            jedis.del(queue.getName());
        }
    }

    @VisibleForTesting
    protected Redis getConnection() {
        return this.connection;
    }

}
