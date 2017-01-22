package me.dilek.wordclock.layout;

import java.time.LocalDateTime;

public class MinuteGenerator {

	public Intervals generate() {
		LocalDateTime time = LocalDateTime.parse("2013-04-13T00:00:00");
		LocalDateTime nextDay = time.plusDays(1);
		Intervals intervals = new Intervals();
		while(time.isBefore(nextDay)) {
			intervals.add(new TimeValue(time));
			time = time.plusMinutes(1);
		}
		return intervals;
	}

}
