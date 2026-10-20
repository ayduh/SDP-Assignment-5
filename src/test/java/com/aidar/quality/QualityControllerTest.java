package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aidar.external.FakeNetworkSpeedProvider;
import com.aidar.model.StreamRequest;

/** The runtime-update idea end to end: preference change -> listener -> context -> new quality. */
class QualityControllerTest {
    private final FakeNetworkSpeedProvider network = new FakeNetworkSpeedProvider(50.0);
    private final List<String> reports = new ArrayList<>();
    private UserPreferences preferences;
    private QualityContext context;
    private QualityController controller;

    @BeforeEach
    void setUp() {
        preferences = new UserPreferences(new StreamRequest(2160, 120, "premium", false));
        context = new QualityContext(new AutoQualityStrategy(), preferences.getCurrent(), network);
        controller = new QualityController(context, new QualitySelector(), network, reports::add);
        preferences.addListener(controller);
    }

    @Test
    void autoStrategyRecalculatesWhenDataSaverIsSwitchedOn() {
        assertEquals("2160p 60fps", context.getVideoQuality());

        preferences.setDataSaverEnabled(true);
        assertEquals("480p 30fps", context.getVideoQuality());

        preferences.setDataSaverEnabled(false);
        assertEquals("2160p 60fps", context.getVideoQuality());
    }

    @Test
    void autoStrategyRecalculatesWhenTheAccountTierChanges() {
        preferences.setAccountTier("standard");
        assertEquals("720p 30fps", context.getVideoQuality());
    }

    @Test
    void manualChoiceKeepsItsStrategyWhenPreferencesChange() {
        controller.chooseManually("HIGHEST");
        preferences.setDataSaverEnabled(true);

        assertInstanceOf(HighestQualityStrategy.class, context.getStrategy());
    }

    @Test
    void automaticChoiceSwapsTheStrategyWhenPreferencesChange() {
        controller.chooseAutomatically();
        assertInstanceOf(HighestQualityStrategy.class, context.getStrategy());

        preferences.setDataSaverEnabled(true);
        assertInstanceOf(AutoQualityStrategy.class, context.getStrategy());
        assertEquals("480p 30fps", context.getVideoQuality());

        preferences.setDataSaverEnabled(false);
        assertInstanceOf(HighestQualityStrategy.class, context.getStrategy());
    }

    @Test
    void switchingFromAutomaticBackToManualStopsTheSelector() {
        controller.chooseAutomatically();
        controller.chooseManually("LOWEST");
        preferences.setAccountTier("standard");

        assertInstanceOf(LowestQualityStrategy.class, context.getStrategy());
    }

    @Test
    void everyUpdateIsReportedWithStrategyAndQuality() {
        preferences.setDataSaverEnabled(true);

        assertEquals(1, reports.size());
        assertEquals("AutoQualityStrategy -> 480p 30fps", reports.get(0));
    }

    @Test
    void contextReceivesTheLatestRequest() {
        preferences.setAccountTier("standard");
        assertTrue(context.getRequest().getUserAccountTier().equals("standard"));
    }
}
