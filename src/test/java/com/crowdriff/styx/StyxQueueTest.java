package com.crowdriff.styx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StyxQueueTest {

    private Styx styx = null;
    private StyxQueue q = null;

    @Before
    public void setUp() throws Exception {
        styx = new Styx(new String[] { "localhost:6700", "localhost:6701", "localhost:6702" });
        TestUtils.clearStorage(styx.getConnection());
        q = styx.getQueue("testQ");
    }

    @After
    public void tearDown() throws Exception {
        TestUtils.clearStorage(styx.getConnection());
    }

    @Test
    public void testPutOneEach() throws Exception {
        addSet(q);

        List<Jedis> all = styx.getConnection().getAll();
        for(Jedis j : all) {
            assertEquals(new Long(1), j.llen("testQ"));
        }
    }

    @Test
    public void testPutTwoEach() throws Exception {
        addSet(q);
        addSet(q);

        List<Jedis> all = styx.getConnection().getAll();
        for(Jedis j : all) {
            assertEquals(new Long(2), j.llen("testQ"));
        }
    }

    @Test
    public void testGetOneEach() throws Exception {
        addSet(q);

        assertTrue(q.get().contains("hello world"));
        assertTrue(q.get().contains("hello world"));
        assertTrue(q.get().contains("hello world"));
    }

    @Test
    public void testGetTwoEach() throws Exception {
        addSet(q);
        addSet(q);

        assertTrue(q.get().contains("hello world"));
        assertTrue(q.get().contains("hello world"));
        assertTrue(q.get().contains("hello world"));
        assertTrue(q.get().contains("hello world"));
        assertTrue(q.get().contains("hello world"));
        assertTrue(q.get().contains("hello world"));
    }

    @Test
    public void testQueueSizeMethod() throws Exception {
        addSet(q);
        assertEquals(3, q.size());

        q.get();
        assertEquals(2, q.size());

        q.get();
        assertEquals(1, q.size());
    }

    /**
     * Helpers
     */
    private void addSet(StyxQueue q) throws Exception {
        q.put("hello world 1");
        q.put("hello world 2");
        q.put("hello world 3");
    }
}
