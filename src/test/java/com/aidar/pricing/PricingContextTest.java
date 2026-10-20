package com.aidar.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.aidar.model.SubscriptionRequest;

class PricingContextTest {
    private final SubscriptionRequest request = new SubscriptionRequest(1000, 12, 3);

    @Test
    void delegatesToTheStrategyGivenInTheConstructor() {
        PricingContext context = new PricingContext(new StandardPricing());
        assertEquals(12_000, context.calculatePrice(request));
    }

    @Test
    void setStrategyChangesTheResultAtRuntime() {
        PricingContext context = new PricingContext(new StandardPricing());
        context.setStrategy(new AnnualPrepayPricing());
        assertEquals(10_000, context.calculatePrice(request));

        context.setStrategy(new FamilyPlanPricing());
        assertEquals(12 * (1000 + 2 * 499), context.calculatePrice(request));
    }

    @Test
    void usesExactlyTheStrategyItWasGiven() {
        PricingStrategy fixedPrice = r -> 42; // any implementation of the interface works
        PricingContext context = new PricingContext(fixedPrice);
        assertSame(fixedPrice, context.getStrategy());
        assertEquals(42, context.calculatePrice(request));
    }

    @Test
    void rejectsMissingStrategy() {
        assertThrows(NullPointerException.class, () -> new PricingContext(null));
        assertThrows(NullPointerException.class, () -> new PricingContext(new StandardPricing()).setStrategy(null));
    }
}
