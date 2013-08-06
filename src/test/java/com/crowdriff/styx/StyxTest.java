package com.crowdriff.styx;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class StyxTest {

    @Test
    public void testConstructor() throws Exception {
        Styx styx = new Styx(new String[] { "localhost:6700", "localhost:6701", "localhost:6702"});
        assertNotNull(styx);
    }

    @Test
    public void testGetQueue() throws Exception {
        Styx styx = new Styx(new String[] { "localhost:6700", "localhost:6701", "localhost:6702"});
        StyxQueue q = styx.getQueue("testQ");
        assertEquals("testQ", q.getName());
    }

    @Test
    public void testDeleteQueue() throws Exception {
        Styx styx = new Styx(new String[] { "localhost:6700", "localhost:6701", "localhost:6702"});
        TestUtils.clearStorage(styx.getConnection());

        StyxQueue q = styx.getQueue("testQ");
        q.put("hello world1");
        q.put("hello world2");
        q.put("hello world3");

        styx.deleteQueue(q);
        List<Jedis> all = styx.getConnection().getAll();
        for(Jedis j : all) {
            assertEquals(null, j.get("testQ"));
        }
    }

}
