package com.aidar.quality;

import java.util.Objects;

import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;


public class QualitySelector {
    /** At or below this speed nothing but the lowest quality is sensible. */
    static final double POOR_NETWORK_MBPS = 1.0;
    /** At or above this speed a premium viewer gets the highest quality. */
    static final double FAST_NETWORK_MBPS = 25.0;

    /** Manual choice: the user picked a mode by name. */
    public VideoQualityStrategy selectStrategy(String userSelectedMode) {
        Objects.requireNonNull(userSelectedMode, "userSelectedMode can't be null");
        return switch (userSelectedMode.toUpperCase()) {
            case "AUTO" -> new AutoQualityStrategy();
            case "HIGHEST" -> new HighestQualityStrategy();
            case "LOWEST" -> new LowestQualityStrategy();
            default -> throw new IllegalArgumentException("Invalid user selected mode: " + userSelectedMode);
        };
    }

    /** Automatic choice: the current situation decides. Rules are checked top to bottom. */
    public VideoQualityStrategy selectAutomatically(StreamRequest request, NetworkSpeedProvider network) {
        double speed = network.getCurrentSpeedMbps();
        boolean premium = "premium".equals(request.getUserAccountTier());

        // Rule 1: a poor connection can't carry more than the lowest quality.
        if (speed <= POOR_NETWORK_MBPS) {
            return new LowestQualityStrategy();
        }
        // Rule 2: premium viewer, fast network, Data Saver off -> take everything the plan allows.
        if (premium && !request.isDataSaverEnabled() && speed >= FAST_NETWORK_MBPS) {
            return new HighestQualityStrategy();
        }
        // Rule 3: everything else is adaptive (it honours Data Saver, tier and device itself).
        return new AutoQualityStrategy();
    }
}
