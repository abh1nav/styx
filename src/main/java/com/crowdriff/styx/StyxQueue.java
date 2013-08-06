package com.crowdriff.styx;

import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;

public class StyxQueue {

    private final Redis connection;
    private final String name;

    protected StyxQueue(Redis connection, String name) {
        this.connection = connection;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public String get() throws Exception {
        Jedis jedis = connection.getReader();
        if(null == jedis) {
            throw new Exception("No Redis connections are active");
        }

        String message = jedis.lpop(this.name);
        return null == message || message.equals("nil") ? null : message;
    }

    public void put(String message) throws Exception {
        Jedis jedis = connection.getWriter();
        if(null == jedis) {
            throw new Exception("No Redis connections are active");
        }

        jedis.rpush(this.name, message);
    }

    public int size() {
        int size = 0;
        for(Jedis jedis : connection.getAll()) {
            size += jedis.llen(this.name);
        }
        return size;
    }

}
