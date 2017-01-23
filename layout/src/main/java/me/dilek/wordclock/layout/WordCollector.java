package me.dilek.wordclock.layout;

import java.text.MessageFormat;
import java.util.Set;
import java.util.TreeSet;

public class WordCollector implements TimeTraveler {

	private final Set<String> words;

	public WordCollector(Set<String> words) {
		super();
		this.words = words;
	}

	public WordCollector() {
		this(new TreeSet<>());
	}

	@Override
	public void walk(Intervals intervals) {
		intervals.getValues().stream().forEach(
				(val) -> val.getReadings().stream().forEach((reading) -> this.words.addAll(reading.getWords())));
	}

	public void printCount() {
		System.out.println(MessageFormat.format("{0} words total", words.size()));
	}

	public void printAll() {
		this.words.stream().forEach(System.out::println);

	}

}
