package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.aidar.external.FakeNetworkSpeedProvider;
import com.aidar.model.StreamRequest;

class QualityContextTest {
    private final FakeNetworkSpeedProvider network = new FakeNetworkSpeedProvider(50.0);
    private final StreamRequest request = new StreamRequest(1080, 25, "standard", false);

    @Test
    void delegatesToTheStrategyGivenInTheConstructor() {
        QualityContext context = new QualityContext(new LowestQualityStrategy(), request, network);
        assertEquals("480p 25fps", context.getVideoQuality());
    }

    @Test
    void workingWithSelectorChosenStrategy() {
        QualityContext context = new QualityContext(new QualitySelector().selectStrategy("auto"), request, network);
        assertEquals("720p 25fps", context.getVideoQuality());
    }

    @Test
    void setStrategyChangesBehaviourAtRuntime() {
        QualityContext context = new QualityContext(new LowestQualityStrategy(), request, network);
        context.setStrategy(new AutoQualityStrategy());
        assertEquals("720p 25fps", context.getVideoQuality());
    }

    @Test
    void setRequestGivesTheStrategyNewParameters() {
        QualityContext context = new QualityContext(new AutoQualityStrategy(), request, network);
        context.setRequest(request.withDataSaverEnabled(true));
        assertEquals("480p 25fps", context.getVideoQuality());
    }

    @Test
    void usesTheNetworkProviderAtTheMomentOfTheCall() {
        FakeNetworkSpeedProvider changingNetwork = new FakeNetworkSpeedProvider(50.0);
        QualityContext context = new QualityContext(new AutoQualityStrategy(), request, changingNetwork);
        changingNetwork.setSpeedMbps(0.5);
        assertEquals("480p 25fps", context.getVideoQuality());
    }

    @Test
    void rejectsMissingCollaborators() {
        assertThrows(NullPointerException.class, () -> new QualityContext(null, request, network));
        assertThrows(NullPointerException.class, () -> new QualityContext(new AutoQualityStrategy(), null, network));
        assertThrows(NullPointerException.class, () -> new QualityContext(new AutoQualityStrategy(), request, null));
    }

    @Test
    void exposesCurrentStrategy() {
        VideoQualityStrategy strategy = new HighestQualityStrategy();
        assertSame(strategy, new QualityContext(strategy, request, network).getStrategy());
    }
}
