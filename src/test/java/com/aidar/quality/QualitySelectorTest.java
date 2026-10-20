package com.aidar.quality;

import org.junit.Test;
import static org.junit.Assert.*;

public class QualitySelectorTest {
    @Test 
    public void testSelectStrategy() {
        QualitySelector selector = new QualitySelector();
        VideoQualityStrategy strategyType = selector.selectStrategy("LoWeST");
        assertNotNull(strategyType);
        assertTrue(strategyType instanceof LowestQualityStrategy);
    }
}
