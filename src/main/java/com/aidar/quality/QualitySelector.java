package com.aidar.quality;

public class QualitySelector {
    public VideoQualityStrategy selectStrategy(String userSelectedMode) {
        return switch (userSelectedMode.toUpperCase()) {
            case "AUTO" -> new AutoQualityStrategy();
            case "HIGHEST" -> new HighestQualityStrategy();
            case "LOWEST" -> new LowestQualityStrategy();
            default -> throw new IllegalArgumentException("Invalid user selected mode: " + userSelectedMode);
        };
    }
}
