package me.dilek.wordclock.layout;

import java.util.function.BiFunction;

public class TimeSpelling {

	private BiFunction<Integer, Integer, Boolean> matcher;
	private BiFunction<Integer, Integer, Reading> speller;

	public TimeSpelling(BiFunction<Integer, Integer, Boolean> matcher, BiFunction<Integer, Integer, Reading> speller) {
		this.matcher = matcher;
		this.speller = speller;
	}

	public boolean matches(int hour, int minute) {
		return this.matcher.apply(hour, minute);
	}

	public Reading spell(int hour, int minute) {
		return this.speller.apply(hour, minute);
	}

}
