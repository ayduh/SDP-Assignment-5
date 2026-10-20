package com.aidar;

import java.util.Scanner;

import com.aidar.external.NetworkSpeedProvider;
import com.aidar.external.SimulatedNetworkSpeedProvider;
import com.aidar.model.StreamRequest;
import com.aidar.model.SubscriptionRequest;
import com.aidar.pricing.PricingContext;
import com.aidar.pricing.PricingSelector;
import com.aidar.pricing.PricingStrategy;
import com.aidar.pricing.StandardPricing;
import com.aidar.quality.AutoQualityStrategy;
import com.aidar.quality.QualityContext;
import com.aidar.quality.QualityController;
import com.aidar.quality.QualitySelector;
import com.aidar.quality.UserPreferences;

public class Main {
    private static final int PREMIUM_MONTHLY_PRICE_CENTS = 1599;

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            Demo.runAll();
            return;
        }

        // video quality family: preferences are observable, the controller listens
        NetworkSpeedProvider network = new SimulatedNetworkSpeedProvider();
        UserPreferences preferences = new UserPreferences(new StreamRequest(2160, 120, "standard", false));
        QualityContext qualityContext = new QualityContext(new AutoQualityStrategy(), preferences.getCurrent(), network);
        QualityController qualityController = new QualityController(
                qualityContext, new QualitySelector(), network, message -> System.out.println("[update] " + message));
        preferences.addListener(qualityController);

        // pricing family
        PricingSelector pricingSelector = new PricingSelector();
        PricingContext pricingContext = new PricingContext(new StandardPricing());

        try (Scanner userInput = new Scanner(System.in)) {
            boolean running = true;
            while (running && userInput.hasNextLine()) {
                System.out.print("""

                        MENU
                        1) Video Quality Settings
                        2) Switch Data Saver
                        3) Buy Premium
                        4) Cancel Premium
                        5) Play Video
                        6) Run scripted demo
                        7) Leave
                        >\s""");

                switch (userInput.nextLine().trim()) {
                    case "1" -> {
                        System.out.print("""
                                Choose Video Quality:
                                1 - AUTO    (adaptive strategy)
                                2 - HIGHEST
                                3 - LOWEST
                                4 - SMART   (the Selector decides and re-decides on every change)
                                >\s""");
                        switch (userInput.nextLine().trim()) {
                            case "2" -> qualityController.chooseManually("HIGHEST");
                            case "3" -> qualityController.chooseManually("LOWEST");
                            case "4" -> qualityController.chooseAutomatically();
                            default -> qualityController.chooseManually("AUTO");
                        }
                    }
                    case "2" -> preferences.setDataSaverEnabled(!preferences.getCurrent().isDataSaverEnabled());
                    case "3" -> {
                        int months = askNumber(userInput, "How many months (1-36)? ", 1, 36, 1);
                        int seats = askNumber(userInput, "How many seats (1-6)? ", 1, 6, 1);
                        SubscriptionRequest order = new SubscriptionRequest(PREMIUM_MONTHLY_PRICE_CENTS, months, seats);

                        PricingStrategy chosen = pricingSelector.selectAutomatically(order);
                        pricingContext.setStrategy(chosen);
                        System.out.println("Selector chose " + chosen.getClass().getSimpleName() + ": "
                                + Demo.usd(pricingContext.calculatePrice(order)) + " for " + order);

                        preferences.setAccountTier("premium"); // listeners recalculate the video quality
                    }
                    case "4" -> preferences.setAccountTier("standard");
                    case "5" -> System.out.println("PLAYING VIDEO: " + qualityContext.getVideoQuality()
                            + "  (" + qualityContext.getRequest() + ")");
                    case "6" -> Demo.runAll();
                    case "7" -> running = false;
                    default -> System.out.println("Unknown option.");
                }
            }
        }
    }

    private static int askNumber(Scanner input, String question, int min, int max, int fallback) {
        System.out.print(question);
        try {
            int value = Integer.parseInt(input.nextLine().trim());
            if (value >= min && value <= max) {
                return value;
            }
        } catch (NumberFormatException ignored) {
            // fall through to the default below
        }
        System.out.println("Not a number between " + min + " and " + max + ", using " + fallback + ".");
        return fallback;
    }
}
