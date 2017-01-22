package me.dilek.wordclock.layout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TimeSpeller implements TimeTraveler {

	final static String[] digits = new String[] { "", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz",
			"dokuz", };

	final static String[] decimals = new String[] { "", "on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş",
			"seksen", "doksan", };

	final static String[] objective_digits = new String[] { "", "biri", "ikiyi", "üçü", "dördü", "beşi", "altıyı",
			"yediyi", "sekizi", "dokuzu", };

	final static String[] objective_decimals = new String[] { "", "onu", "yirmiyi", "otuzu", "kırkı", "elliyi",
			"altmışı", "yetmişi", "sekseni", "doksanı", };

	final static String[] dative_digits = new String[] { "", "bire", "ikiye", "üçe", "dörde", "beşe", "altıya",
			"yediye", "sekize", "dokuza", };

	final static String[] dative_decimals = new String[] { "", "ona", "yirmiye", "otuza", "kırka", "elliye", "altmışa",
			"yetmişe", "seksene", "doksana", };

	final static List<TimeSpelling> spellings = Arrays.asList(
			// 00:00
			(when((h, m) -> h == 0 && m == 0)).spell((h, m) -> new Reading("saat").add(neutral(12))),
			// 01-23:00
			(when((h, m) -> h > 0 && m == 0)).spell((h, m) -> new Reading("saat").add(neutral(h))),
			// 13-23:00
			(when((h, m) -> h > 12 && m == 0)).spell((h, m) -> new Reading("saat").add(neutral(h - 12))),
			// 00:10-59
			(when((h, m) -> h == 0 && m > 9)).spell((h, m) -> new Reading("saat").add(neutral(12)).add(neutral(m))),
			// 00:00-59
			(when((h, m) -> (h == 0 || h == 12) && m != 0))
					.spell((h, m) -> new Reading("saat").add(objective(12)).add(neutral(m)).add("geçiyor")),
			// 01-23:10-59
			(when((h, m) -> h > 0 && m > 9)).spell((h, m) -> new Reading("saat").add(neutral(h)).add(neutral(m))),
			// 01-23:00-59
			(when((h, m) -> h > 0 && m != 0))
					.spell((h, m) -> new Reading("saat").add(objective(h)).add(neutral(m)).add("geçiyor")),
			// 13-23:10-59
			(when((h, m) -> h > 12 && m > 9)).spell((h, m) -> new Reading("saat").add(neutral(h - 12)).add(neutral(m))),
			// 13-23:00-59
			(when((h, m) -> h > 12 && m != 0))
					.spell((h, m) -> new Reading("saat").add(objective(h - 12)).add(neutral(m)).add("geçiyor")),
			// çeyrek geçiyor
			(when((h, m) -> h > 0 && m == 15))
					.spell((h, m) -> new Reading("saat").add(objective(h)).add("çeyrek", "geçiyor")),
			// 00:15
			(when((h, m) -> h == 0 && m == 15))
					.spell((h, m) -> new Reading("saat").add(objective(12)).add("çeyrek", "geçiyor")),
			// 13+ çeyrek geçiyor
			(when((h, m) -> h > 12 && m == 15))
					.spell((h, m) -> new Reading("saat").add(objective(h - 12)).add("çeyrek", "geçiyor")),
			// çeyrek var
			(when((h, m) -> m == 45)).spell((h, m) -> new Reading("saat").add(dative(h + 1)).add("çeyrek", "var")),
			// 13+ çeyrek var
			(when((h, m) -> h > 12 && m == 45))
					.spell((h, m) -> new Reading("saat").add(dative(h - 12 + 1)).add("çeyrek", "var")),
			// buçuk
			(when((h, m) -> h > 0 && m == 30)).spell((h, m) -> new Reading("saat").add(neutral(h)).add("buçuk")),
			// 00:30
			(when((h, m) -> h == 0 && m == 30)).spell((h, m) -> new Reading("saat").add(neutral(12)).add("buçuk")),
			// 13+ buçuk
			(when((h, m) -> h > 12 && m == 30)).spell((h, m) -> new Reading("saat").add(neutral(h - 12)).add("buçuk")),
			// yarım
			(when((h, m) -> (h == 0 || h == 12) && m == 30)).spell((h, m) -> new Reading("saat").add("yarım")),
			// var
			(when((h, m) -> h > 0 && m > 39))
					.spell((h, m) -> new Reading("saat").add(dative(h + 1)).add(neutral(60 - m)).add("var")),
			// 00:xx var
			(when((h, m) -> h == 0 && m > 39))
					.spell((h, m) -> new Reading("saat").add(dative(1)).add(neutral(60 - m)).add("var")),
			// 13+ var
			(when((h, m) -> h > 12 && m > 39))
					.spell((h, m) -> new Reading("saat").add(dative(h - 12 + 1)).add(neutral(60 - m)).add("var"))

	);

	public void walk(Intervals intervals) {
		intervals.getValues().stream().forEach((value) -> {
			LocalDateTime time = value.getTime();
			int hour = time.getHour();
			int minute = time.getMinute();
			List<Reading> readings = spellings.stream().filter(s -> s.matches(hour, minute))
					.map((spelling) -> spelling.spell(hour, minute)).collect(Collectors.toList());
			value.addReadings(readings);
		});
	}

	private static SpellingCondition when(BiFunction<Integer, Integer, Boolean> matcher) {
		return new SpellingCondition(matcher);
	}

	private static class SpellingCondition {

		private BiFunction<Integer, Integer, Boolean> matcher;

		public SpellingCondition(BiFunction<Integer, Integer, Boolean> matcher) {
			this.matcher = matcher;
		}

		public TimeSpelling spell(BiFunction<Integer, Integer, Reading> speller) {
			return new TimeSpelling(this.matcher, speller);
		}
	}

	static List<String> neutral(int value) {
		List<String> words = new ArrayList<String>();
		if (value > 9) {
			words.add(decimals[value / 10]);
		}
		if (value % 10 > 0) {
			words.add(digits[value % 10]);
		}
		return words;
	}

	static List<String> objective(int value) {
		List<String> words = new ArrayList<String>();
		if (value % 10 == 0) {
			words.add(objective_decimals[value / 10]);
		} else {
			if (value > 9) {
				words.add(decimals[value / 10]);
			}
			if (value % 10 > 0) {
				words.add(objective_digits[value % 10]);
			}

		}
		return words;
	}

	static List<String> dative(int value) {
		List<String> words = new ArrayList<String>();
		if (value % 10 == 0) {
			words.add(dative_decimals[value / 10]);
		} else {
			if (value > 9) {
				words.add(decimals[value / 10]);
			}
			if (value % 10 > 0) {
				words.add(dative_digits[value % 10]);
			}

		}
		return words;
	}
}
