package com.aidar.quality;

import com.aidar.model.StreamRequest;

public class QualitySelector implements UserPreferencesListener{
    private final QualityContext context;

    public VideoQualityStrategy selectStrategy(String userSelectedMode) {
        return switch (userSelectedMode.toUpperCase()) {
            case "AUTO" -> new AutoQualityStrategy();
            case "HIGHEST" -> new HighestQualityStrategy();
            case "LOWEST" -> new LowestQualityStrategy();
            default -> throw new IllegalArgumentException("Invalid user selected mode: " + userSelectedMode);
        };
    }

    @Override
    void onPreferencesChanged(StreamRequest newRequest) {
        
    }
}
