package com.aidar.quality;

import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class QualityContext {
    private VideoQualityStrategy strategy;
    private StreamRequest request;
    private NetworkSpeedProvider network;

    public QualityContext(VideoQualityStrategy strategy) {
        this.strategy = strategy;
    }

    public void setQualityStrategy(VideoQualityStrategy strategy) {
        this.strategy = strategy;
    }
    
    public String getVideoQuality() {
        return strategy.determineVideoQuality(request, network);
    }

    public void startVideoPlayback(StreamRequest request, NetworkSpeedProvider network) {
        if (request == null) {
            throw new IllegalArgumentException("StreamRequest can't be null");
        }
        if (network == null) {
            throw new IllegalArgumentException("NetworkSpeedProvider can't be null");
        }

        this.request = request;
        this.network = network;
        System.out.println("PLAYING VIDEO: \n" + getVideoQuality());
    }
}