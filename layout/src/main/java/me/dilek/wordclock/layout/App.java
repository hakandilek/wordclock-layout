package me.dilek.wordclock.layout;

import java.util.Set;

import me.dilek.wordclock.layout.ga.GeneticSolver;

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
		
		Set<String> words = wordCollector.getWords();
		int initialPopulation = 100;
		int limitGenerations = 100;
		int cols = 18, rows = 16;
		GeneticSolver solver = new GeneticSolver(words, initialPopulation, limitGenerations, intervals, cols, rows);
		solver.solve();
    }
}
