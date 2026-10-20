package com.aidar.model;

public class StreamRequest {
    private int maxDeviceResolution;
    private int maxDeviceFrameRate;
    private String userAccountTier;
    private boolean isDataSaverEnabled;

    public StreamRequest(int maxDeviceResolution, int maxDeviceFrameRate, String userAccountTier, boolean isDataSaverEnabled) {
        this.maxDeviceResolution = maxDeviceResolution;
        this.maxDeviceFrameRate = maxDeviceFrameRate;
        this.userAccountTier = userAccountTier;
        this.isDataSaverEnabled = isDataSaverEnabled;
    }

    public int getMaxDeviceResolution() {
        return maxDeviceResolution;
    }
    public void setMaxDeviceResolution(int maxDeviceResolution) {
        this.maxDeviceResolution = maxDeviceResolution;
    }

    public int getMaxDeviceFrameRate() {
        return maxDeviceFrameRate;
    }
    public void setMaxDeviceFrameRate(int maxDeviceFrameRate) {
        this.maxDeviceFrameRate = maxDeviceFrameRate;
    }

    public String getUserAccountTier() {
        return userAccountTier;
    }
    public void setUserAccountTier(String userAccountTier) {
        this.userAccountTier = userAccountTier;
    }

    public boolean isDataSaverEnabled() {
        return isDataSaverEnabled;
    }
    public void setDataSaverEnabled(boolean dataSaverEnabled) {
        isDataSaverEnabled = dataSaverEnabled;
    }
}
