package com.aidar.pricing;

import com.aidar.model.SubscriptionRequest;

/** Prepaid years: every full 12 months, 2 months are free. */
public class AnnualPrepayPricing implements PricingStrategy {
    static final int MONTHS_PER_YEAR = 12;
    static final int FREE_MONTHS_PER_YEAR = 2;

    @Override
    public long calculatePriceCents(SubscriptionRequest request) {
        int freeMonths = (request.getMonths() / MONTHS_PER_YEAR) * FREE_MONTHS_PER_YEAR;
        return (long) request.getMonthlyPriceCents() * (request.getMonths() - freeMonths);
    }
}
