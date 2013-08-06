package com.crowdriff.styx;

import redis.clients.jedis.Jedis;

public class TestUtils {

    public static void clearStorage(Redis redis) {
        for(Jedis j : redis.getAll()) {
            j.flushAll();
        }
    }

}
