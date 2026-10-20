package com.aidar.pricing;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.aidar.model.SubscriptionRequest;

class PricingSelectorTest {
    private final PricingSelector selector = new PricingSelector();

    // ---- manual choice

    @Test
    void manualChoiceKnowsAllPlans() {
        assertInstanceOf(StandardPricing.class, selector.selectStrategy("STANDARD"));
        assertInstanceOf(AnnualPrepayPricing.class, selector.selectStrategy("ANNUAL"));
        assertInstanceOf(FamilyPlanPricing.class, selector.selectStrategy("FAMILY"));
    }

    @Test
    void manualChoiceIsCaseInsensitive() {
        assertInstanceOf(FamilyPlanPricing.class, selector.selectStrategy("fAmIlY"));
    }

    @Test
    void unknownPlanIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> selector.selectStrategy("GOLD"));
    }

    // ---- automatic choice (one test per rule)

    @Test
    void rule1_twoSeatsGiveFamily() {
        assertInstanceOf(FamilyPlanPricing.class, selector.selectAutomatically(new SubscriptionRequest(1599, 1, 2)));
    }

    @Test
    void rule1_oneSeatIsNotFamily() {
        assertInstanceOf(StandardPricing.class, selector.selectAutomatically(new SubscriptionRequest(1599, 1, 1)));
    }

    @Test
    void rule1_winsOverRule2() {
        assertInstanceOf(FamilyPlanPricing.class, selector.selectAutomatically(new SubscriptionRequest(1599, 24, 3)));
    }

    @Test
    void rule2_twelveMonthsGiveAnnual() {
        assertInstanceOf(AnnualPrepayPricing.class, selector.selectAutomatically(new SubscriptionRequest(1599, 12, 1)));
    }

    @Test
    void rule2_elevenMonthsAreNotAnnual() {
        assertInstanceOf(StandardPricing.class, selector.selectAutomatically(new SubscriptionRequest(1599, 11, 1)));
    }

    @Test
    void rule3_everythingElseIsStandard() {
        assertInstanceOf(StandardPricing.class, selector.selectAutomatically(new SubscriptionRequest(1599, 1, 1)));
    }
}
