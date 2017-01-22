package me.dilek.wordclock.layout;

import java.util.ArrayList;
import java.util.List;

public class Reading {

	private final List<String> words = new ArrayList<>();

	public Reading(String... words) {
		for (String word : words) {
			this.words.add(word);
		}
	}

	@Override
	public String toString() {
		return words.toString();
	}

	public Reading add(List<String> words) {
		this.words.addAll(words);
		return this;
	}

	public Reading add(String... words) {
		for (String word : words) {
			this.words.add(word);
		}
		return this;
	}

}
