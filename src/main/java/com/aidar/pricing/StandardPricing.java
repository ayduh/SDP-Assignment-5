package com.aidar.pricing;

import com.aidar.model.SubscriptionRequest;

/** Pays the base price for every month, no extras, no discounts. */
public class StandardPricing implements PricingStrategy {
    @Override
    public long calculatePriceCents(SubscriptionRequest request) {
        return (long) request.getMonthlyPriceCents() * request.getMonths();
    }
}
