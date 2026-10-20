package com.aidar.quality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.aidar.model.StreamRequest;

class UserPreferencesTest {
    private final UserPreferences preferences = new UserPreferences(new StreamRequest(1080, 60, "standard", false));
    private final List<StreamRequest> received = new ArrayList<>();

    @Test
    void listenerReceivesTheNewSnapshotWhenDataSaverChanges() {
        preferences.addListener(received::add);
        preferences.setDataSaverEnabled(true);

        assertEquals(1, received.size());
        assertTrue(received.get(0).isDataSaverEnabled());
        assertEquals(received.get(0), preferences.getCurrent());
    }

    @Test
    void listenerReceivesTheNewSnapshotWhenTierChanges() {
        preferences.addListener(received::add);
        preferences.setAccountTier("premium");

        assertEquals("premium", received.get(0).getUserAccountTier());
    }

    @Test
    void settingTheSameValueDoesNotNotifyAnyone() {
        preferences.addListener(received::add);
        preferences.setDataSaverEnabled(false);
        preferences.setAccountTier("standard");

        assertTrue(received.isEmpty());
    }

    @Test
    void everyListenerIsNotified() {
        List<StreamRequest> second = new ArrayList<>();
        preferences.addListener(received::add);
        preferences.addListener(second::add);
        preferences.setDataSaverEnabled(true);

        assertEquals(1, received.size());
        assertEquals(1, second.size());
    }

    @Test
    void snapshotsAlreadyHandedOutAreNotChangedByLaterUpdates() {
        preferences.addListener(received::add);
        preferences.setDataSaverEnabled(true);
        preferences.setDataSaverEnabled(false);

        assertTrue(received.get(0).isDataSaverEnabled());
        assertFalse(received.get(1).isDataSaverEnabled());
    }
}
