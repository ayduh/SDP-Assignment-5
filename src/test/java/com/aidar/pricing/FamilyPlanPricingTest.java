package com.aidar.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.aidar.model.SubscriptionRequest;

class FamilyPlanPricingTest {
    private final FamilyPlanPricing strategy = new FamilyPlanPricing();

    @Test
    void oneSeatCostsTheBasePrice() {
        assertEquals(1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 1, 1)));
    }

    @Test
    void everyExtraSeatAddsTheFixedFee() {
        assertEquals(1599 + 2 * 499, strategy.calculatePriceCents(new SubscriptionRequest(1599, 1, 3)));
    }

    @Test
    void seatFeeIsChargedForEveryMonth() {
        assertEquals(3 * (1599 + 499), strategy.calculatePriceCents(new SubscriptionRequest(1599, 3, 2)));
    }

    @Test
    void seatsAboveTheLimitAreNotCharged() {
        assertEquals(1599 + 5 * 499, strategy.calculatePriceCents(new SubscriptionRequest(1599, 1, 6)));
        assertEquals(1599 + 5 * 499, strategy.calculatePriceCents(new SubscriptionRequest(1599, 1, 20)));
    }

    @Test
    void ignoresFullYears() {
        assertEquals(12 * 1599, strategy.calculatePriceCents(new SubscriptionRequest(1599, 12, 1)));
    }
}
