package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chronotimer.*;

public class RacerTest {

	@Test
	public void testRacersNumbers() {
		Racer r1 = new Racer(1);
		assertEquals(1, r1.getNumber());
		Racer r2 = new Racer(215);
		assertEquals(215, r2.getNumber());
		Racer r3 = new Racer(999);
		assertEquals(999, r3.getNumber());

	}

	/*
	 * Exception type not yet defined with in class, used Exception for
	 * ambiguity
	 */
	@Test(expected = Exception.class)
	public void testRacerOutofBoundsNumber() {
		Racer r1 = new Racer(0);
		assertEquals(0, r1.getNumber());
		Racer r2 = new Racer(1000);
		assertEquals(1000, r2.getNumber());
		Racer r3 = new Racer(-22);
		assertEquals(-22, r3.getNumber());
	}
}
