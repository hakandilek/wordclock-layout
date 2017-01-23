package me.dilek.wordclock.layout.ga;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import me.dilek.wordclock.layout.Layout;

public class LayoutGeneratorTest {

	@Test
	public void testOfDefaultAlphabet() {
		Layout layout = LayoutGenerator.of(10, 10);
		assertNotNull(layout);
		System.out.println(layout);
		assertEquals(100, layout.words().size());
		assertEquals(10, layout.rowCount());
		for (int i = 0; i < 10; i++) {
			List<String> row = layout.words(i);
			assertEquals(10, row.size());
		}
	}

	@Test
	public void testOfAlphabet() {
		Layout layout = LayoutGenerator.of(10, 10, new char[] { '1' });
		assertNotNull(layout);
		System.out.println(layout);
		assertEquals(100, layout.words().size());
		assertEquals(10, layout.rowCount());
		for (int i = 0; i < 10; i++) {
			List<String> row = layout.words(i);
			assertEquals(10, row.size());
			for (String word : row) {
				assertEquals("1", word);
			}
		}
	}

	@Test
	public void testOfDictionary() {
		String[] words = new String[] { "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz", "on",
				"yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan", };

		Layout layout = LayoutGenerator.of(10, 10, Arrays.asList(words));
		assertNotNull(layout);
		System.out.println(layout);
		assertEquals(words.length, layout.words().size());
		assertTrue(layout.rowCount() <= 10);
		for (int i = 0; i < layout.rowCount(); i++) {
			List<String> row = layout.words(i);
			assertNotNull(row);
			assertFalse(row.isEmpty());
		}
	}
}
