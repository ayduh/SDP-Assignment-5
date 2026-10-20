package com.aidar.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SubscriptionRequestTest {
    @Test
    void rejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new SubscriptionRequest(-1, 1, 1));
    }

    @Test
    void rejectsZeroMonths() {
        assertThrows(IllegalArgumentException.class, () -> new SubscriptionRequest(1599, 0, 1));
    }

    @Test
    void rejectsZeroSeats() {
        assertThrows(IllegalArgumentException.class, () -> new SubscriptionRequest(1599, 1, 0));
    }
}
