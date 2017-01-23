package me.dilek.wordclock.layout.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import me.dilek.wordclock.layout.Layout;

public class LayoutGenerator {
	static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	public static Layout of(int cols, int rows) {
		return of(cols, rows, alphabet);
	}

	public static Layout of(int cols, int rows, char[] alphabet) {
		Layout layout = new Layout(rows, cols);
		Random rand = new Random();
		int cells = cols * rows;
		for (int i = 0; i < cells; i++) {
			char c = alphabet[rand.nextInt(alphabet.length)];
			layout.add(c + "");
		}
		return layout;
	}

	public static Layout of(int cols, int rows, Collection<String> dictionary) {
		Layout layout = new Layout(rows, cols);
		Random rand = new Random();
		List<String> words = new ArrayList<>(dictionary);
		while (!words.isEmpty()) {
			String word = words.get(rand.nextInt(words.size()));
			layout.add(word);
			words.remove(word);
		}
		return layout;
	}

}
