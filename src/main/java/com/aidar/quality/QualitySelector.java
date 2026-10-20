package com.aidar.quality;

public class QualitySelector {
    public VideoQualityStrategy selectStrategy(String userSelectedMode) {
        return switch (userSelectedMode) {
            case "auto" -> new AutoQualityStrategy();
            case "highest" -> new HighestQualityStrategy();
            case "lowest" -> new LowestQualityStrategy();
            default -> throw new IllegalArgumentException("Invalid user selected mode: " + userSelectedMode);
        };
    }
}
