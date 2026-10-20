package com.aidar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StreamRequestTest {
    private final StreamRequest original = new StreamRequest(1080, 60, "standard", false);

    @Test
    void withersReturnNewSnapshotsAndKeepTheOriginalUntouched() {
        StreamRequest saver = original.withDataSaverEnabled(true);
        StreamRequest premium = original.withAccountTier("premium");

        assertTrue(saver.isDataSaverEnabled());
        assertEquals("premium", premium.getUserAccountTier());
        assertFalse(original.isDataSaverEnabled());
        assertEquals("standard", original.getUserAccountTier());
    }

    @Test
    void equalityIsByValue() {
        assertEquals(original, new StreamRequest(1080, 60, "standard", false));
        assertNotEquals(original, original.withDataSaverEnabled(true));
    }

    @Test
    void tierIsRequired() {
        assertThrows(NullPointerException.class, () -> new StreamRequest(1080, 60, null, false));
    }
}
