package me.dilek.wordclock.layout;

/**
 * Main app
 */
public class App 
{
    public static void main( String[] args )
    {
    	WordCollector wordCollector = new WordCollector();
		TimeTraveler reader = new CompositeTraveler( new TimeSpeller(), new TimePrinter(), wordCollector);
    	Intervals intervals = new MinuteGenerator().generate();
		reader.walk(intervals);
		
		wordCollector.printAll();
		wordCollector.printCount();
    }
}
