package com.crowdriff.styx;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalPerformanceTest {

    private static final Logger logger = LoggerFactory.getLogger(LocalPerformanceTest.class);

    @Test
    public void testPerformance() throws Exception {
        Styx styx = new Styx(new String[]{"localhost:6700", "localhost:6701", "localhost:6702"});
        TestUtils.clearStorage(styx.getConnection());
        StyxQueue q = styx.getQueue("testQ");
        PerformanceTest pTest = new PerformanceTest(q);
        int entries = pTest.doRun();
        int rate = (int) (entries/10.0);
        logger.info("Local Performance: {} puts per second", String.valueOf(rate));
    }

}
