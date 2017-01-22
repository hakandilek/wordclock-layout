package me.dilek.wordclock.layout;

import java.text.MessageFormat;
import java.util.List;

public class TimePrinter implements TimeTraveler {

	public void walk(Intervals intervals) {
		List<TimeValue> values = intervals.getValues();
		for (TimeValue value : values) {
			System.out.println(value);
		}
		System.out.println(MessageFormat.format("{0} intervals", values.size()));
	}

}
