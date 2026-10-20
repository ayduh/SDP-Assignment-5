package com.aidar.pricing;

import com.aidar.model.SubscriptionRequest;

public interface PricingStrategy {
    /** Total price in cents for the whole subscription. Never negative. */
    long calculatePriceCents(SubscriptionRequest request);
}
