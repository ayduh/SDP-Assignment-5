package com.aidar.pricing;

import java.util.Objects;

import com.aidar.model.SubscriptionRequest;

/** Chooses a pricing strategy. The only place with if / switch on the choice. */
public class PricingSelector {
    static final int MIN_FAMILY_SEATS = 2;
    static final int ANNUAL_FROM_MONTHS = 12;

    /** Manual choice: the customer names the plan. */
    public PricingStrategy selectStrategy(String plan) {
        Objects.requireNonNull(plan, "plan can't be null");
        return switch (plan.toUpperCase()) {
            case "STANDARD" -> new StandardPricing();
            case "ANNUAL" -> new AnnualPrepayPricing();
            case "FAMILY" -> new FamilyPlanPricing();
            default -> throw new IllegalArgumentException("Unknown plan: " + plan);
        };
    }

    /** Automatic choice: the order decides. Rules are checked top to bottom. */
    public PricingStrategy selectAutomatically(SubscriptionRequest request) {
        // Rule 1: more than one seat means a family customer.
        if (request.getFamilySeats() >= MIN_FAMILY_SEATS) {
            return new FamilyPlanPricing();
        }
        // Rule 2: a long commitment is rewarded with free months.
        if (request.getMonths() >= ANNUAL_FROM_MONTHS) {
            return new AnnualPrepayPricing();
        }
        // Rule 3: everybody else pays the plain price.
        return new StandardPricing();
    }
}
