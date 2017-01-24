package me.dilek.wordclock.layout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LayoutTest {

	private Layout layout;
	private Reading iki;
	private Reading kirkIki;
	private Reading yuzKirkIki;
	private Reading kirkBir;
	private Reading kirkUcIki;

	@Before
	public void prepare() {
		layout = new Layout(4, 4);
		layout.add("kırk");
		layout.add("üç");
		layout.add("iki");

		iki = new Reading("iki");
		kirkBir = new Reading("kırk", "bir");
		kirkIki = new Reading("kırk", "iki");
		kirkUcIki = new Reading("kırk", "üç", "iki");
		yuzKirkIki = new Reading("yüz", "kırk", "iki");
	}

	@Test
	public void testAdd() {
		assertFalse(layout.add("yirmi"));
		assertTrue(layout.add("iki"));
	}

	@Test
	public void testContains() {
		assertTrue(layout.contains(iki));
		assertTrue(layout.contains(kirkIki));
		assertTrue(layout.contains(kirkUcIki));
		assertFalse(layout.contains(kirkBir));
		assertFalse(layout.contains(yuzKirkIki));
	}

}
