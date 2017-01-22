package me.dilek.wordclock.layout;

import java.util.ArrayList;
import java.util.List;

public class CompositeTraveler implements TimeTraveler {

	final List<TimeTraveler> travelers = new ArrayList<>();

	public CompositeTraveler() {
		super();
	}

	public CompositeTraveler(TimeTraveler... travelers) {
		for (TimeTraveler traveler : travelers) {
			this.travelers.add(traveler);
		}
	}

	public void walk(Intervals intervals) {
		travelers.stream().forEach((t) -> t.walk(intervals));
	}

	public CompositeTraveler add(TimeTraveler traveler) {
		return this;
	}

}
