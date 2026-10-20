package com.aidar.external;

/** Simulated network: reports a fixed speed (50 Mbps by default). */
public class SimulatedNetworkSpeedProvider implements NetworkSpeedProvider {
    private final double speedMbps;

    public SimulatedNetworkSpeedProvider() {
        this(50.0);
    }

    public SimulatedNetworkSpeedProvider(double speedMbps) {
        this.speedMbps = speedMbps;
    }

    @Override
    public double getCurrentSpeedMbps() {
        return speedMbps;
    }
}
