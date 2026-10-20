package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.aidar.external.FakeNetworkSpeedProvider;
import com.aidar.model.StreamRequest;

class AutoQualityStrategyTest {
    private final AutoQualityStrategy strategy = new AutoQualityStrategy();

    @Test
    void standardAccountIsCappedAt720pAnd30fps() {
        StreamRequest request = new StreamRequest(1080, 60, "standard", false);
        assertEquals("720p 30fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(5.0)));
    }

    @Test
    void standardAccountOnSmallDeviceGets480p() {
        StreamRequest request = new StreamRequest(480, 60, "standard", false);
        assertEquals("480p 30fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(5.0)));
    }

    @Test
    void premiumAccountOn4kDeviceGets2160pAnd60fps() {
        StreamRequest request = new StreamRequest(2160, 120, "premium", false);
        assertEquals("2160p 60fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(10.0)));
    }

    @Test
    void premiumAccountFollowsTheDeviceResolution() {
        FakeNetworkSpeedProvider network = new FakeNetworkSpeedProvider(10.0);
        assertEquals("1080p 60fps", strategy.determineVideoQuality(new StreamRequest(1080, 60, "premium", false), network));
        assertEquals("720p 60fps", strategy.determineVideoQuality(new StreamRequest(720, 60, "premium", false), network));
        assertEquals("480p 60fps", strategy.determineVideoQuality(new StreamRequest(480, 60, "premium", false), network));
    }

    @Test
    void dataSaverForcesLowQualityEvenForPremium() {
        StreamRequest request = new StreamRequest(1080, 60, "premium", true);
        assertEquals("480p 30fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(10.0)));
    }

    @Test
    void poorNetworkForcesLowQuality() {
        StreamRequest request = new StreamRequest(2160, 120, "premium", false);
        assertEquals("480p 30fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(1.0)));
    }

    @Test
    void frameRateNeverExceedsWhatTheDeviceCanShow() {
        StreamRequest request = new StreamRequest(1080, 25, "standard", false);
        assertEquals("720p 25fps", strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(50.0)));
    }
}
