package com.aidar.model;

/** What a customer wants to buy: base monthly price, length and number of family seats. Money is in cents. */
public final class SubscriptionRequest {
    private final int monthlyPriceCents;
    private final int months;
    private final int familySeats;

    public SubscriptionRequest(int monthlyPriceCents, int months, int familySeats) {
        if (monthlyPriceCents < 0) {
            throw new IllegalArgumentException("monthlyPriceCents can't be negative");
        }
        if (months < 1) {
            throw new IllegalArgumentException("months must be at least 1");
        }
        if (familySeats < 1) {
            throw new IllegalArgumentException("familySeats must be at least 1");
        }
        this.monthlyPriceCents = monthlyPriceCents;
        this.months = months;
        this.familySeats = familySeats;
    }

    public int getMonthlyPriceCents() {
        return monthlyPriceCents;
    }

    public int getMonths() {
        return months;
    }

    public int getFamilySeats() {
        return familySeats;
    }

    @Override
    public String toString() {
        return months + " month(s), " + familySeats + " seat(s), base " + monthlyPriceCents / 100 + "." + String.format("%02d", monthlyPriceCents % 100);
    }
}
