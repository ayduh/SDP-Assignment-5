package com.aidar.pricing;

import com.aidar.model.SubscriptionRequest;

/**
 * AFTER refactoring: same public API as before, but no pricing logic and no switch here.
 * The selector chooses, the context delegates, the strategies calculate.
 */
public class PricingService {
    private final PricingSelector selector = new PricingSelector();

    /** Manual choice: the customer names the plan. */
    public long priceCents(String plan, SubscriptionRequest request) {
        return new PricingContext(selector.selectStrategy(plan)).calculatePrice(request);
    }

    /** Automatic choice: the situation picks the plan. */
    public long smartPriceCents(SubscriptionRequest request) {
        return new PricingContext(selector.selectAutomatically(request)).calculatePrice(request);
    }
}
