package com.aidar.quality;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class LowestQualityStrategy implements VideoQualityStrategy {
    @Override
    public String determineVideoQuality(StreamRequest request, NetworkSpeedProvider network) {
        int fps = Math.min(30, request.getMaxDeviceFrameRate());
        return "480p " + fps + "fps";
    }
}
