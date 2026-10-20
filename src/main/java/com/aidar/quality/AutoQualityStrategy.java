package com.aidar.quality;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class AutoQualityStrategy implements VideoQualityStrategy{
    @Override
    public String determineVideoQuality(StreamRequest request, NetworkSpeedProvider network) {
        int premiumFPS = (request.getMaxDeviceFrameRate() > 60) ? 60 : request.getMaxDeviceFrameRate();
        int standardFPS = (request.getMaxDeviceFrameRate() > 30) ? 30 : request.getMaxDeviceFrameRate();
        String videoResolution;

        if (request.isDataSaverEnabled() || network.getCurrentSpeedMbps() <= 1.0) {
            videoResolution = "480p";
            return videoResolution + " " + standardFPS + "fps";
        }

        if (request.getUserAccountTier().equals("premium")) {
            if (request.getMaxDeviceResolution() >= 2160) {
                videoResolution = "2160p";
            } else if (request.getMaxDeviceResolution() >= 1080) {
                videoResolution = "1080p";
            } else if (request.getMaxDeviceResolution() >= 720) {
                videoResolution = "720p";
            } else {
                videoResolution = "480p";
            }
            return videoResolution + " " + premiumFPS + "fps";
        }
        else { // standard tier
            if (request.getMaxDeviceResolution() >= 720) {
                videoResolution = "720p";
                return videoResolution + " " + standardFPS + "fps";
            } else {
                videoResolution = "480p";
                return videoResolution + " " + standardFPS + "fps";
            }
        }
    }
}
