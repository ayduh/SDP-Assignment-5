package com.aidar.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.aidar.model.SubscriptionRequest;

class AnnualPrepayPricingTest {
    private final AnnualPrepayPricing strategy = new AnnualPrepayPricing();

    @Test
    void oneYearPaysTenMonths() {
        assertEquals(10 * 1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 12, 1)));
    }

    @Test
    void twoYearsPayTwentyMonths() {
        assertEquals(20 * 1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 24, 1)));
    }

    @Test
    void partialYearGetsNoFreeMonths() {
        assertEquals(11 * 1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 11, 1)));
    }

    @Test
    void monthsAfterAFullYearAreChargedNormally() {
        // 14 months = one full year (2 free) + 2 extra months
        assertEquals(12 * 1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 14, 1)));
    }

    @Test
    void ignoresSeats() {
        assertEquals(10 * 1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 12, 4)));
    }
}
