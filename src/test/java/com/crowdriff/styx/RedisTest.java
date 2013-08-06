package com.crowdriff.styx;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class RedisTest {

    Redis redis = null;

    @Before
    public void setUp() throws Exception {
        redis = new Redis("localhost:6700");
    }

    @Test
    public void testGet() throws Exception {
        Jedis jedis = redis.getReader();
        assertNotNull(jedis);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(1, redis.size());
    }

    @Test
    public void testRemove() throws Exception {
        Jedis jedis = redis.getReader();
        redis.remove(jedis);
        assertEquals(0, redis.size());
    }

}
