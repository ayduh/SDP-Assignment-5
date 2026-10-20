package com.aidar.pricing;

import com.aidar.model.SubscriptionRequest;

/** Base price plus a fixed fee for every extra seat, with a limit on seats. */
public class FamilyPlanPricing implements PricingStrategy {
    static final int EXTRA_SEAT_FEE_CENTS = 499;
    static final int MAX_SEATS = 6;

    @Override
    public long calculatePriceCents(SubscriptionRequest request) {
        int extraSeats = Math.min(request.getFamilySeats(), MAX_SEATS) - 1;
        long pricePerMonth = request.getMonthlyPriceCents() + (long) EXTRA_SEAT_FEE_CENTS * extraSeats;
        return pricePerMonth * request.getMonths();
    }
}
