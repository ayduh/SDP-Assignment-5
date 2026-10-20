package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.aidar.external.FakeNetworkSpeedProvider;
import com.aidar.model.StreamRequest;

/** One check, run on EVERY strategy of the family (and on several situations each). */
class QualityStrategiesParameterizedTest {

        @Test
        void resultIsWellFormedAndNeverExceedsTheDeviceFrameRate() {
        List<VideoQualityStrategy> strategies = List.of(
                new AutoQualityStrategy(), new HighestQualityStrategy(), new LowestQualityStrategy());
        List<StreamRequest> requests = List.of(
                new StreamRequest(480, 20, "standard", false),
                new StreamRequest(1080, 60, "standard", true),
                new StreamRequest(1080, 24, "premium", false),
                new StreamRequest(2160, 165, "premium", true));
        List<Double> speeds = List.of(0.5, 5.0, 100.0);

                for (VideoQualityStrategy strategy : strategies) {
                        for (StreamRequest request : requests) {
                                for (double speed : speeds) {
                                        String quality = strategy.determineVideoQuality(request, new FakeNetworkSpeedProvider(speed));

                                        assertTrue(quality.matches("\\d+p \\d+fps"), "unexpected format: " + quality);
                                        int fps = Integer.parseInt(quality.substring(quality.indexOf(' ') + 1, quality.length() - 3));
                                        assertAll(
                                                        () -> assertTrue(fps > 0, "fps must be positive"),
                                                        () -> assertTrue(fps <= request.getMaxDeviceFrameRate(), "fps " + fps + " exceeds device limit"));
                                }
                        }
                }
    }
}
