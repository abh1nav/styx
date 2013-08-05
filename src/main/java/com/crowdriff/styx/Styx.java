package com.crowdriff.styx;

import redis.clients.jedis.Jedis;

public class Styx {

    public Styx(String[] redisHosts) {
        Redis.I.init(redisHosts);
    }

    public StyxQueue getQueue(String queueName) {
        return new StyxQueue(queueName);
    }

    public void deleteQueue(StyxQueue queue) {
        for(Jedis jedis : Redis.I.getAll()) {
            jedis.del(queue.getName());
        }
    }

}
