package com.aidar.quality;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public interface VideoQualityStrategy {
    public String determineVideoQuality(StreamRequest request, NetworkSpeedProvider networkSpeedProvider);
}
