package com.aidar.model;

public class StreamRequest {
    private String maxDeviceResolution;
    private String maxDeviceFrameRate;
    private String userAccountTier;
    private boolean isDataSaverEnabled;

    public StreamRequest(String maxDeviceResolution, String maxDeviceFrameRate, String userAccountTier, boolean isDataSaverEnabled) {
        this.maxDeviceResolution = maxDeviceResolution;
        this.maxDeviceFrameRate = maxDeviceFrameRate;
        this.userAccountTier = userAccountTier;
        this.isDataSaverEnabled = isDataSaverEnabled;
    }

    public String getMaxDeviceResolution() {
        return maxDeviceResolution;
    }
    public void setMaxDeviceResolution(String maxDeviceResolution) {
        this.maxDeviceResolution = maxDeviceResolution;
    }

    public String getMaxDeviceFrameRate() {
        return maxDeviceFrameRate;
    }
    public void setMaxDeviceFrameRate(String maxDeviceFrameRate) {
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
