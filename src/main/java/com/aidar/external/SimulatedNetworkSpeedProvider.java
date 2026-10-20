package com.aidar.external;

public class SimulatedNetworkSpeedProvider implements NetworkSpeedProvider {
    @Override
    public double getCurrentSpeedMbps() {
        // Simulate network speed (e.g., 50 Mbps)
        return 50.0;
    }
}
