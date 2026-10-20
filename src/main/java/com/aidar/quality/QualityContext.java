package com.aidar.quality;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class QualityContext {
    private VideoQualityStrategy strategy;
    private StreamRequest request;
    private NetworkSpeedProvider network;

    public QualityContext(VideoQualityStrategy strategy, StreamRequest request, NetworkSpeedProvider network) {
        this.strategy = strategy;
        this.request = request;
        this.network = network;
    }

    public void setQualityStrategy(VideoQualityStrategy strategy) {
        this.strategy = strategy;
    }

    public String getVideoQuality() {
        return strategy.determineVideoQuality(request, network);
    }
}