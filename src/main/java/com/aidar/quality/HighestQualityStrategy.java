package com.aidar.quality;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class HighestQualityStrategy implements VideoQualityStrategy{
    @Override
    public String determineVideoQuality(StreamRequest request, NetworkSpeedProvider network) {
        int premiumFPS = (request.getMaxDeviceFrameRate() > 60) ? 60 : request.getMaxDeviceFrameRate();
        int standardFPS = (request.getMaxDeviceFrameRate() > 30) ? 30 : request.getMaxDeviceFrameRate();
        String videoResolution;

        if (request.getUserAccountTier().equals("premium")) {
            videoResolution = "2160p";
            return (videoResolution + " " + Integer.toString(premiumFPS) + "fps");
        }
        else {
            videoResolution = "720p";
            return (videoResolution + " " + Integer.toString(standardFPS) + "fps");
        }
    }
}
