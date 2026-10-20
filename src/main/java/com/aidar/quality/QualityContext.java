package com.aidar.quality;

import java.util.Objects;

import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

/** Strategy context: holds the current strategy and its parameters, and only delegates. */
public class QualityContext {
    private VideoQualityStrategy strategy;
    private StreamRequest request;
    private final NetworkSpeedProvider network;

    public QualityContext(VideoQualityStrategy strategy, StreamRequest request, NetworkSpeedProvider network) {
        this.strategy = Objects.requireNonNull(strategy, "strategy can't be null");
        this.request = Objects.requireNonNull(request, "StreamRequest can't be null");
        this.network = Objects.requireNonNull(network, "NetworkSpeedProvider can't be null");
    }

    public void setStrategy(VideoQualityStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy can't be null");
    }

    /** Replaces the parameters the strategy works with (called when preferences change). */
    public void setRequest(StreamRequest request) {
        this.request = Objects.requireNonNull(request, "StreamRequest can't be null");
    }

    public VideoQualityStrategy getStrategy() {
        return strategy;
    }

    public StreamRequest getRequest() {
        return request;
    }

    public String getVideoQuality() {
        return strategy.determineVideoQuality(request, network);
    }
}
