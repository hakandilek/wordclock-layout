package me.dilek.wordclock.layout;

import java.util.ArrayList;
import java.util.List;

public class Intervals {

	private List<TimeValue> values = new ArrayList<>();

	public List<TimeValue> getValues() {
		return values;
	}

	public void add(TimeValue value) {
		values.add(value);
	}

}
