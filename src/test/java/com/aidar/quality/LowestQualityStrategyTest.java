package com.aidar.quality;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;

public class LowestQualityStrategyTest {

    @Test 
    public void testDetermineVideoQuality() {
        LowestQualityStrategy strategy = new LowestQualityStrategy();
        StreamRequest request = new StreamRequest(1080, 60, "standard", false);


        NetworkSpeedProvider fakeNetwork = () -> 100.0;

        String result = strategy.determineVideoQuality(request, fakeNetwork);
        assertEquals("480p 30fps", result);
    }

    @Test 
    public void testDetermineVideoQualityPremium() {
          LowestQualityStrategy strategy = new LowestQualityStrategy();
          StreamRequest request = new StreamRequest(2160, 120, "premium", false);
          
          NetworkSpeedProvider fakeNetwork = () -> 10.0;
    
          String result = strategy.determineVideoQuality(request, fakeNetwork);
          assertEquals("480p 30fps", result);
     }
}
