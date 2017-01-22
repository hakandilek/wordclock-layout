package me.dilek.wordclock.layout;

/**
 * Main app
 */
public class App 
{
    public static void main( String[] args )
    {
    	TimeTraveler reader = new CompositeTraveler( new TimeSpeller(), new TimePrinter());
    	Intervals intervals = new MinuteGenerator().generate();
		reader.walk(intervals);
    }
}
