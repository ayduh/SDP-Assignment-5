package com.aidar;
import com.aidar.BadImplementationVideoQuality;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BadImplementationVideoQuality video = new BadImplementationVideoQuality();
        video.displayVideo("saver", null, 0, "500kb/s");
    }
}
