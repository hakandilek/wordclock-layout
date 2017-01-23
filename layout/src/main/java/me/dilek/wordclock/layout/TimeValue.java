package me.dilek.wordclock.layout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeValue {

	private final LocalDateTime time;
	private final List<Reading> readings = new ArrayList<>();

	public TimeValue(LocalDateTime time) {
		this.time = time;
	}

	public LocalDateTime getTime() {
		return time;
	}

	@Override
	public String toString() {
		return String.format("[%1$tH:%1$tM] - %2$s ", time, readings);
	}

	public void addReading(Reading reading) {
		this.readings.add(reading);
	}

	public void addReadings(List<Reading> readings) {
		this.readings.addAll(readings);
	}

	public List<Reading> getReadings() {
		return readings;
	}
	
}
