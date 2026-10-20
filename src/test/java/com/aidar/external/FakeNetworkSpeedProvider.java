package com.aidar.external;

/** Test double for the network: returns whatever speed the test sets, nothing random. */
public class FakeNetworkSpeedProvider implements NetworkSpeedProvider {
    private double speedMbps;

    public FakeNetworkSpeedProvider(double speedMbps) {
        this.speedMbps = speedMbps;
    }

    public void setSpeedMbps(double speedMbps) {
        this.speedMbps = speedMbps;
    }

    @Override
    public double getCurrentSpeedMbps() {
        return speedMbps;
    }
}
