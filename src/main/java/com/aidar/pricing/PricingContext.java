package com.aidar.pricing;

import java.util.Objects;

import com.aidar.model.SubscriptionRequest;

/** Strategy context: holds the current pricing strategy and only delegates to it. */
public class PricingContext {
    private PricingStrategy strategy;

    public PricingContext(PricingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy can't be null");
    }

    public void setStrategy(PricingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy can't be null");
    }

    public PricingStrategy getStrategy() {
        return strategy;
    }

    public long calculatePrice(SubscriptionRequest request) {
        return strategy.calculatePriceCents(request);
    }
}
