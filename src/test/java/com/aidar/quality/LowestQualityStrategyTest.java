package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.aidar.external.FakeNetworkSpeedProvider;
import com.aidar.model.StreamRequest;

class LowestQualityStrategyTest {
    private final LowestQualityStrategy strategy = new LowestQualityStrategy();

    @Test
    void standardAccountGets480p30fps() {
        StreamRequest request = new StreamRequest(1080, 60, "standard", false);
        assertEquals("480p 30fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(100.0)));
    }

    @Test
    void premiumAccountAlsoGets480p30fps() {
        StreamRequest request = new StreamRequest(2160, 120, "premium", false);
        assertEquals("480p 30fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(10.0)));
    }

    @Test
    void frameRateIsLimitedByTheDevice() {
        StreamRequest request = new StreamRequest(480, 20, "standard", false);
        assertEquals("480p 20fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(10.0)));
    }
}
