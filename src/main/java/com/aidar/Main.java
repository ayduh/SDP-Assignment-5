package com.aidar;
import com.aidar.quality.BadImplementationVideoQuality;
/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        BadImplementationVideoQuality video = new BadImplementationVideoQuality();
        video.displayVideo("saver", null, 0, "500kb/s");
    }
}
