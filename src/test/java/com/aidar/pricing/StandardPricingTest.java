package com.aidar.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.aidar.model.SubscriptionRequest;

class StandardPricingTest {
    private final StandardPricing strategy = new StandardPricing();

    @Test
    void multipliesBasePriceByMonths() {
        assertEquals(4797, strategy.calculatePriceCents(new SubscriptionRequest(1599, 3, 1)));
    }

    @Test
    void ignoresSeats() {
        assertEquals(1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 1, 5)));
    }

    @Test
    void ignoresFullYears() {
        assertEquals(24 * 1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 24, 1)));
    }

    @Test
    void freeBasePriceStaysFree() {
        assertEquals(0, strategy.calculatePriceCents(new SubscriptionRequest(0, 12, 1)));
    }
}
