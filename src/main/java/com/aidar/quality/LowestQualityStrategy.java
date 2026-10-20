package com.aidar.quality;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class LowestQualityStrategy implements VideoQualityStrategy {
    @Override
    public String determineVideoQuality(StreamRequest request, NetworkSpeedProvider network) {
        return "480p 30fps";
    }
}
