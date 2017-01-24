package me.dilek.wordclock.layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TimeSpellerTest {

	private Intervals intervals;
	private TimeSpeller speller;
	
	@Before
	public void prepare() {
		speller = new TimeSpeller();
		intervals = new MinuteGenerator().generate();
	}
	
	@Test
	public void testWalk() {
		speller.walk(intervals);
		assertNotNull(intervals);
		assertNotNull(intervals.getValues());
		assertEquals(60*24, intervals.getValues().size());
		for (TimeValue value : intervals.getValues()) {
			assertNotNull(value);
			assertNotNull(value.getReadings());
			assertFalse(value.getReadings().isEmpty());
		}
	}

}
