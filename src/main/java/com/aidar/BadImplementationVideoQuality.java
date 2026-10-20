package com.aidar;

public class BadImplementationVideoQuality {
    public void displayVideo(
        String type, 
        String deviceMaxResolution, 
        float deviceMaxFrameRate,
        String internetSpeed
    ) {
        if ("AUTO".equalsIgnoreCase(type)) {
            if (deviceMaxResolution == "4k" && deviceMaxFrameRate == 165) {
                System.out.println("Video with HIGH quality");
            }
            if (deviceMaxResolution == "1080p" && deviceMaxFrameRate == 60) {
                System.out.println("Video with MEDIUM quality");
            }
            if (deviceMaxResolution == "720p" && deviceMaxFrameRate == 30) {
                System.out.println("Video with BAD quality");
            }
        } else if ("SAVER".equalsIgnoreCase(type)) {
            System.out.println("Video with SAVER quality ");
        } else if ("DEVICE_MAX".equalsIgnoreCase(type)) {
            System.out.println("Video with MAX quality");
        }
    }
}
