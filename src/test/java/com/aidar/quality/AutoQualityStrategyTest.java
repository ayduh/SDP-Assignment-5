package com.aidar.quality;
import org.junit.Test;
import com.aidar.external.NetworkSpeedProvider;
import com.aidar.model.StreamRequest;
import static org.junit.Assert.*;

public class AutoQualityStrategyTest {

    @Test
    public void testDetermineVideoQuality() {
        AutoQualityStrategy strategy = new AutoQualityStrategy();
        StreamRequest request = new StreamRequest(1080, 60, "standard", false);
        
        NetworkSpeedProvider fakeNetwork = () -> 5.0;

        String result = strategy.determineVideoQuality(request, fakeNetwork);
        assertEquals("720p 30fps", result);
    }
    
    @Test 
    public void testDetermineVideoQualityPremium() {
          AutoQualityStrategy strategy = new AutoQualityStrategy();
          StreamRequest request = new StreamRequest(2160, 120, "premium", false);
          
          NetworkSpeedProvider fakeNetwork = () -> 10.0;
    
          String result = strategy.determineVideoQuality(request, fakeNetwork);
          assertEquals("2160p 60fps", result);
     }

     @Test
     public void testDetermineVideoQualityDataSaver() {
          AutoQualityStrategy strategy = new AutoQualityStrategy();
          StreamRequest request = new StreamRequest(1080, 60, "premium", true);
          
          NetworkSpeedProvider fakeNetwork = () -> 10.0;
    
          String result = strategy.determineVideoQuality(request, fakeNetwork);
          assertEquals("480p 30fps", result);
     }
}
