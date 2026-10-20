package com.aidar;

import java.util.List;

import com.aidar.external.NetworkSpeedProvider;
import com.aidar.external.SimulatedNetworkSpeedProvider;
import com.aidar.model.StreamRequest;
import com.aidar.model.SubscriptionRequest;
import com.aidar.pricing.AnnualPrepayPricing;
import com.aidar.pricing.FamilyPlanPricing;
import com.aidar.pricing.PricingContext;
import com.aidar.pricing.PricingSelector;
import com.aidar.pricing.PricingStrategy;
import com.aidar.pricing.StandardPricing;
import com.aidar.quality.AutoQualityStrategy;
import com.aidar.quality.HighestQualityStrategy;
import com.aidar.quality.LowestQualityStrategy;
import com.aidar.quality.QualityContext;
import com.aidar.quality.QualityController;
import com.aidar.quality.QualitySelector;
import com.aidar.quality.UserPreferences;
import com.aidar.quality.VideoQualityStrategy;

/** Scripted, non-interactive demo of everything */
public final class Demo {
    private Demo() { }

    public static void runAll() {
        qualityDemo();
        pricingDemo();
    }

    static String usd(long cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }

    static void section(String title) {
        System.out.println("\n=== " + title + " ===");
    }

// Family 1 Video Quality
    static void qualityDemo() {
        QualitySelector selector = new QualitySelector();
        NetworkSpeedProvider fast = new SimulatedNetworkSpeedProvider(50.0);
        StreamRequest premium4k = new StreamRequest(2160, 120, "premium", false);

        section("VIDEO QUALITY 1/4: same input, every strategy");
        System.out.println("Input: " + premium4k + ", network 50 Mbps");
        List<VideoQualityStrategy> all = List.of(new AutoQualityStrategy(), new HighestQualityStrategy(), new LowestQualityStrategy());
        for (VideoQualityStrategy strategy : all) {
            System.out.printf("  %-24s -> %s%n", strategy.getClass().getSimpleName(), strategy.determineVideoQuality(premium4k, fast));
        }

        section("VIDEO QUALITY 2/4: manual choice vs. automatic choice by the Selector");
        record Situation(String label, StreamRequest request, NetworkSpeedProvider network) { }
        List<Situation> situations = List.of(
                new Situation("premium, 50 Mbps", premium4k, fast),
                new Situation("premium, Data Saver ON", premium4k.withDataSaverEnabled(true), fast),
                new Situation("premium, 0.5 Mbps", premium4k, new SimulatedNetworkSpeedProvider(0.5)),
                new Situation("standard, 50 Mbps", premium4k.withAccountTier("standard"), fast));
        VideoQualityStrategy manual = selector.selectStrategy("HIGHEST");
        for (Situation s : situations) {
            VideoQualityStrategy automatic = selector.selectAutomatically(s.request(), s.network());
            System.out.printf("  %-24s manual HIGHEST: %-12s automatic: %-22s -> %s%n", s.label(),
                    manual.determineVideoQuality(s.request(), s.network()),
                    automatic.getClass().getSimpleName(),
                    automatic.determineVideoQuality(s.request(), s.network()));
        }

        section("VIDEO QUALITY 3/4: switching the strategy at runtime");
        QualityContext context = new QualityContext(new LowestQualityStrategy(), premium4k, fast);
        System.out.println("  new QualityContext(Lowest)   -> " + context.getVideoQuality());
        context.setStrategy(new AutoQualityStrategy());
        System.out.println("  setStrategy(Auto)            -> " + context.getVideoQuality());
        context.setStrategy(new HighestQualityStrategy());
        System.out.println("  setStrategy(Highest)         -> " + context.getVideoQuality());

        section("VIDEO QUALITY 4/4: preferences change at runtime (UserPreferencesListener)");
        UserPreferences preferences = new UserPreferences(new StreamRequest(2160, 120, "standard", false));
        QualityContext live = new QualityContext(new AutoQualityStrategy(), preferences.getCurrent(), fast);
        QualityController controller = new QualityController(live, selector, fast, msg -> System.out.println("      [update] " + msg));
        preferences.addListener(controller);

        System.out.println("  Manual mode AUTO, standard account:   " + live.getVideoQuality());
        System.out.println("  User buys Premium:");
        preferences.setAccountTier("premium");
        System.out.println("  User switches Data Saver ON:");
        preferences.setDataSaverEnabled(true);
        System.out.println("  User hands the decision to the Selector (SMART):");
        controller.chooseAutomatically();
        System.out.println("  User switches Data Saver OFF (Selector swaps the strategy):");
        preferences.setDataSaverEnabled(false);
        System.out.println("  User switches Data Saver ON again (Selector swaps back):");
        preferences.setDataSaverEnabled(true);
    }

// Family 2 Pricing
    static void pricingDemo() {
        PricingSelector selector = new PricingSelector();

        section("PRICING 1/3: same input, every strategy");
        SubscriptionRequest order = new SubscriptionRequest(1599, 12, 3);
        System.out.println("Input: " + order);
        List<PricingStrategy> all = List.of(new StandardPricing(), new AnnualPrepayPricing(), new FamilyPlanPricing());
        for (PricingStrategy strategy : all) {
            System.out.printf("  %-24s -> %s%n", strategy.getClass().getSimpleName(), usd(strategy.calculatePriceCents(order)));
        }

        section("PRICING 2/3: manual choice vs. automatic choice by the Selector");
        record Customer(String label, SubscriptionRequest order) { }
        List<Customer> customers = List.of(
                new Customer("solo, 1 month", new SubscriptionRequest(1599, 1, 1)),
                new Customer("solo, 1 year", new SubscriptionRequest(1599, 12, 1)),
                new Customer("family of 4, 1 month", new SubscriptionRequest(1599, 1, 4)),
                new Customer("family of 4, 1 year", new SubscriptionRequest(1599, 12, 4)));
        PricingStrategy manualChoice = selector.selectStrategy("STANDARD");
        for (Customer c : customers) {
            PricingStrategy automatic = selector.selectAutomatically(c.order());
            System.out.printf("  %-22s manual STANDARD: %-9s automatic: %-22s -> %s%n", c.label(),
                    usd(manualChoice.calculatePriceCents(c.order())),
                    automatic.getClass().getSimpleName(),
                    usd(automatic.calculatePriceCents(c.order())));
        }

        section("PRICING 3/3: switching the strategy at runtime");
        PricingContext context = new PricingContext(new StandardPricing());
        System.out.println("  new PricingContext(Standard)  -> " + usd(context.calculatePrice(order)));
        context.setStrategy(new AnnualPrepayPricing());
        System.out.println("  setStrategy(AnnualPrepay)     -> " + usd(context.calculatePrice(order)));
        context.setStrategy(new FamilyPlanPricing());
        System.out.println("  setStrategy(FamilyPlan)       -> " + usd(context.calculatePrice(order)));
    }
}
