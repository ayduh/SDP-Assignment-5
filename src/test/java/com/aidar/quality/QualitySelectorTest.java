package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.aidar.external.FakeNetworkSpeedProvider;
import com.aidar.model.StreamRequest;

class QualitySelectorTest {
    private final QualitySelector selector = new QualitySelector();

    // ---- manual choice (by name)

    @Test
    void manualChoiceIsCaseInsensitive() {
        assertInstanceOf(LowestQualityStrategy.class, selector.selectStrategy("LoWeST"));
    }

    @Test
    void manualChoiceKnowsAllModes() {
        assertInstanceOf(AutoQualityStrategy.class, selector.selectStrategy("AUTO"));
        assertInstanceOf(HighestQualityStrategy.class, selector.selectStrategy("HIGHEST"));
        assertInstanceOf(LowestQualityStrategy.class, selector.selectStrategy("LOWEST"));
    }

    @Test
    void unknownModeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> selector.selectStrategy("ULTRA"));
    }

    // ---- automatic choice (one test per rule)

    private static StreamRequest request(String tier, boolean dataSaver) {
        return new StreamRequest(2160, 120, tier, dataSaver);
    }

    @Test
    void rule1_poorNetworkGivesLowest() {
        assertInstanceOf(LowestQualityStrategy.class,
                selector.selectAutomatically(request("premium", false), new FakeNetworkSpeedProvider(0.5)));
    }

    @Test
    void rule1_poorNetworkBoundaryIsInclusive() {
        assertInstanceOf(LowestQualityStrategy.class,
                selector.selectAutomatically(request("premium", false), new FakeNetworkSpeedProvider(1.0)));
        assertInstanceOf(AutoQualityStrategy.class,
                selector.selectAutomatically(request("premium", false), new FakeNetworkSpeedProvider(1.01)));
    }

    @Test
    void rule1_winsOverEverythingElse() {
        assertInstanceOf(LowestQualityStrategy.class,
                selector.selectAutomatically(request("standard", true), new FakeNetworkSpeedProvider(0.1)));
    }

    @Test
    void rule2_premiumOnFastNetworkGivesHighest() {
        assertInstanceOf(HighestQualityStrategy.class,
                selector.selectAutomatically(request("premium", false), new FakeNetworkSpeedProvider(50.0)));
    }

    @Test
    void rule2_fastNetworkBoundaryIsInclusive() {
        assertInstanceOf(HighestQualityStrategy.class,
                selector.selectAutomatically(request("premium", false), new FakeNetworkSpeedProvider(25.0)));
        assertInstanceOf(AutoQualityStrategy.class,
                selector.selectAutomatically(request("premium", false), new FakeNetworkSpeedProvider(24.9)));
    }

    @Test
    void rule2_dataSaverBlocksHighest() {
        assertInstanceOf(AutoQualityStrategy.class,
                selector.selectAutomatically(request("premium", true), new FakeNetworkSpeedProvider(50.0)));
    }

    @Test
    void rule2_standardAccountNeverGetsHighestAutomatically() {
        assertInstanceOf(AutoQualityStrategy.class,
                selector.selectAutomatically(request("standard", false), new FakeNetworkSpeedProvider(100.0)));
    }

    @Test
    void rule3_everythingElseIsAuto() {
        assertInstanceOf(AutoQualityStrategy.class,
                selector.selectAutomatically(request("standard", false), new FakeNetworkSpeedProvider(10.0)));
    }
}
