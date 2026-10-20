package com.aidar.pricing;

import com.aidar.model.SubscriptionRequest;

/**
 * BEFORE refactoring: every pricing variant AND the rules for choosing one live in this single class.
 * Adding a plan means editing the switch below (violates Open/Closed).
 */
public class PricingService {

    /** Manual choice: the customer names the plan. */
    public long priceCents(String plan, SubscriptionRequest request) {
        switch (plan.toUpperCase()) {
            case "STANDARD":
                return (long) request.getMonthlyPriceCents() * request.getMonths();
            case "ANNUAL": {
                int freeMonths = (request.getMonths() / 12) * 2;
                return (long) request.getMonthlyPriceCents() * (request.getMonths() - freeMonths);
            }
            case "FAMILY": {
                int extraSeats = Math.min(request.getFamilySeats(), 6) - 1;
                long perMonth = request.getMonthlyPriceCents() + 499L * extraSeats;
                return perMonth * request.getMonths();
            }
            default:
                throw new IllegalArgumentException("Unknown plan: " + plan);
        }
    }

    /** Automatic choice: the situation picks the plan. */
    public long smartPriceCents(SubscriptionRequest request) {
        if (request.getFamilySeats() >= 2) {
            return priceCents("FAMILY", request);
        }
        if (request.getMonths() >= 12) {
            return priceCents("ANNUAL", request);
        }
        return priceCents("STANDARD", request);
    }
}
