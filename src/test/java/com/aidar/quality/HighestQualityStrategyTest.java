package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.aidar.external.FakeNetworkSpeedProvider;
import com.aidar.model.StreamRequest;

class HighestQualityStrategyTest {
    private final HighestQualityStrategy strategy = new HighestQualityStrategy();

    @Test
    void standardAccountGetsTheHighestStandardQuality() {
        StreamRequest request = new StreamRequest(480, 60, "standard", false);
        assertEquals("720p 30fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(100.0)));
    }

    @Test
    void premiumAccountGetsTheHighestPremiumQuality() {
        StreamRequest request = new StreamRequest(1500, 120, "premium", false);
        assertEquals("2160p 60fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(10.0)));
    }

    @Test
    void frameRateIsLimitedByTheDevice() {
        StreamRequest request = new StreamRequest(1080, 24, "standard", false);
        assertEquals("720p 24fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(10.0)));
    }

    @Test
    void ignoresDataSaverAndNetwork() {
        StreamRequest request = new StreamRequest(2160, 120, "premium", true);
        assertEquals("2160p 60fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(0.1)));
    }
}
