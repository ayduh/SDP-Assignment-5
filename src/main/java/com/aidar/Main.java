package com.aidar;
import java.nio.channels.NetworkChannel;
import java.util.Scanner;

import com.aidar.external.NetworkSpeedProvider;
import com.aidar.external.SimulatedNetworkSpeedProvider;
import com.aidar.model.StreamRequest;
import com.aidar.quality.BadImplementationVideoQuality;
import com.aidar.quality.QualityContext;
import com.aidar.quality.QualitySelector;
import com.aidar.quality.VideoQualityStrategy;

public class Main 
{
    public static void main( String[] args )
    {
        BadImplementationVideoQuality video = new BadImplementationVideoQuality();
        video.displayVideo("saver", null, 0, "500kb/s");

        // -------------------------- //

        try (Scanner userInput = new Scanner(System.in)) {
            String selectedQuality = "AUTO";
            boolean running = true;
            boolean isDataSaverEnabled = false;
            String accountTier = "standard";

            NetworkSpeedProvider network = new SimulatedNetworkSpeedProvider();
            QualitySelector qualitySelector = new QualitySelector();

            VideoQualityStrategy initialStrategy = qualitySelector.selectStrategy(selectedQuality);
            QualityContext qualityContext = new QualityContext(initialStrategy);

            while (running) {
                System.out.print("""
                \nMENU
                1) Video Quality Settings
                2) Switch Data Saver
                3) Buy Premium
                4) Cancel Premium
                5) Play Video
                6) Leave
            """);

                String userChoice = userInput.nextLine();

                if (userChoice.equals("1")) {
                    System.out.print("""
                        Choose Video Quality:
                        1 - AUTO
                        2 - HIGHEST
                        3 - LOWEST    
                    """);
                    String qualityChoice = userInput.nextLine();
                    selectedQuality = switch (qualityChoice) {
                        case "1" -> "AUTO";
                        case "2" -> "HIGHEST";
                        case "3" -> "LOWEST";
                        default -> "AUTO";
                    };
                } else if (userChoice.equals("2")) {
                    isDataSaverEnabled = !isDataSaverEnabled;
                } else if (userChoice.equals("3")) {
                    accountTier = "premium";
                } else if (userChoice.equals("4")) {
                    accountTier = "standard";
                } else if (userChoice.equals("5")) {
                    StreamRequest request = new StreamRequest(480,20, accountTier, isDataSaverEnabled);

                    VideoQualityStrategy activeStrategy = qualitySelector.selectStrategy(selectedQuality);

                    qualityContext.setQualityStrategy(activeStrategy);

                    qualityContext.startVideoPlayback(request, network);
                } else if (userChoice.equals("6")) {
                    running = false;
                }
            }
        }
    }
}