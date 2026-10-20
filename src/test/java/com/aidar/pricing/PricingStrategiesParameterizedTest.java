package com.aidar.pricing;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.aidar.model.SubscriptionRequest;

/** One check, run on EVERY strategy of the family (and on several requests each). */
class PricingStrategiesParameterizedTest {

    @Test
    void priceIsNeverNegativeAndNeverAboveTheFamilyMaximum() {
        List<PricingStrategy> strategies = List.of(new StandardPricing(), new AnnualPrepayPricing(), new FamilyPlanPricing());
        List<SubscriptionRequest> requests = List.of(
                new SubscriptionRequest(0, 1, 1),
                new SubscriptionRequest(1599, 1, 1),
                new SubscriptionRequest(1599, 11, 2),
                new SubscriptionRequest(1599, 12, 1),
                new SubscriptionRequest(1599, 36, 6),
                new SubscriptionRequest(1599, 5, 100));

            for (PricingStrategy strategy : strategies) {
                for (SubscriptionRequest request : requests) {
                long price = strategy.calculatePriceCents(request);
                long absoluteCeiling = (long) (request.getMonthlyPriceCents()
                    + 5 * FamilyPlanPricing.EXTRA_SEAT_FEE_CENTS) * request.getMonths();

                assertAll(
                    () -> assertTrue(price >= 0, "price must not be negative"),
                    () -> assertTrue(price <= absoluteCeiling,
                        "price " + price + " is above the ceiling " + absoluteCeiling));
                }
            }
    }
}
