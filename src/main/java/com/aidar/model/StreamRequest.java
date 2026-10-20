package com.aidar.model;

import java.util.Objects;

public final class StreamRequest {
    private final int maxDeviceResolution;
    private final int maxDeviceFrameRate;
    private final String userAccountTier;
    private final boolean isDataSaverEnabled;

    public StreamRequest(int maxDeviceResolution, int maxDeviceFrameRate, String userAccountTier, boolean isDataSaverEnabled) {
        this.maxDeviceResolution = maxDeviceResolution;
        this.maxDeviceFrameRate = maxDeviceFrameRate;
        this.userAccountTier = Objects.requireNonNull(userAccountTier, "userAccountTier can't be null");
        this.isDataSaverEnabled = isDataSaverEnabled;
    }

    public int getMaxDeviceResolution() {
        return maxDeviceResolution;
    }

    public int getMaxDeviceFrameRate() {
        return maxDeviceFrameRate;
    }

    public String getUserAccountTier() {
        return userAccountTier;
    }

    public boolean isDataSaverEnabled() {
        return isDataSaverEnabled;
    }

    public StreamRequest withDataSaverEnabled(boolean enabled) {
        return new StreamRequest(maxDeviceResolution, maxDeviceFrameRate, userAccountTier, enabled);
    }

    public StreamRequest withAccountTier(String tier) {
        return new StreamRequest(maxDeviceResolution, maxDeviceFrameRate, tier, isDataSaverEnabled);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof StreamRequest that)) return false;
        return maxDeviceResolution == that.maxDeviceResolution
                && maxDeviceFrameRate == that.maxDeviceFrameRate
                && isDataSaverEnabled == that.isDataSaverEnabled
                && userAccountTier.equals(that.userAccountTier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxDeviceResolution, maxDeviceFrameRate, userAccountTier, isDataSaverEnabled);
    }

    @Override
    public String toString() {
        return maxDeviceResolution + "p/" + maxDeviceFrameRate + "fps device, tier=" + userAccountTier
                + ", dataSaver=" + (isDataSaverEnabled ? "ON" : "OFF");
    }
}
