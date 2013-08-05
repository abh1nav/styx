package com.crowdriff.styx;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class RedisTest {

    @Before
    public void setUp() throws Exception {
        Redis.I.init("localhost:6700");
    }

    @Test
    public void testGet() throws Exception {
        Jedis jedis = Redis.I.get();
        assertNotNull(jedis);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(1, Redis.I.size());
    }

    @Test
    public void testRemove() throws Exception {
        Jedis jedis = Redis.I.get();
        Redis.I.remove(jedis);
        assertEquals(0, Redis.I.size());
    }
}
