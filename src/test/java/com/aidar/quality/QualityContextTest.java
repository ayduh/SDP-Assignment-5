package com.aidar.quality;
import com.aidar.model.StreamRequest;
import static org.junit.Assert.assertEquals;
import com.aidar.external.NetworkSpeedProvider;
import org.junit.Test;

public class QualityContextTest {
    
    @Test
    public void testGetVideoQuality() {
        StreamRequest request = new StreamRequest(1080, 60, "standard", false);
        NetworkSpeedProvider network = () -> 50.0;
        QualityContext context = new QualityContext(new LowestQualityStrategy());
        assertEquals("480p 30fps", context.getVideoQuality());
    }

    @Test 
    public void testGetVideoQualityWithSelector() {
        QualitySelector selector = new QualitySelector();
        NetworkSpeedProvider network = () -> 50.0;
        StreamRequest request = new StreamRequest(1080, 25, "standard", false);
        QualityContext context = new QualityContext(selector.selectStrategy("auto"));       

        assertEquals("720p 25fps", context.getVideoQuality());
    }
}
