package com.aidar.quality;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class HighestQualityStrategyTest {

    @Test 
    public void testDetermineVideoQuality() {
        HighestQualityStrategy strategy = new HighestQualityStrategy();
        StreamRequest request = new StreamRequest(480, 60, "standard", false);

        NetworkSpeedProvider fakeNetwork = () -> 100.0;

        String result = strategy.determineVideoQuality(request, fakeNetwork);
        assertEquals("720p 30fps", result);
    }

    @Test 
    public void testDetermineVideoQualityPremium() {
          HighestQualityStrategy strategy = new HighestQualityStrategy();
          StreamRequest request = new StreamRequest(1500, 120, "premium", false);
          
          NetworkSpeedProvider fakeNetwork = () -> 10.0;
    
          String result = strategy.determineVideoQuality(request, fakeNetwork);
          assertEquals("2160p 60fps", result);
     }
}
