package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import chronotimer.Timer;

public class TimerTest {
	@Test
	public void test0ArgumentConstructor() {
		Timer t = new Timer();
		assertFalse(t.getTime() == null);
	}

	@Test
	public void test1ArgumentConstructor() {
		Timer t = new Timer(null);
		assertFalse(t.getTime() == null);
		t = new Timer("11:00");
		assertFalse(t.getTime() == null);
		assertFalse(t.getTime() == "11:00");
		assertFalse(t.getTime() == "11:00:00.00");
		t = new Timer("11:00:00.00");
		assertEquals(t.getTime(), "11:00:00.00");
	}

	@Test
	public void testGetTime() {
		Timer t = new Timer("11:00:00.00");
		assertEquals(t.getTime(), "11:00:00.00");
		// make sure that getTime didin't change the time
		assertEquals(t.getTime(), "11:00:00.00");
	}

	@Test
	public void testSetTime() {
		Timer t = new Timer("11:00:00.00");
		assertEquals(t.getTime(), "11:00:00.00");
		t.setTime("12:00:01.00");
		assertEquals(t.getTime(), "12:00:01.00");
		t.setTime("12:00:02.00");
		assertEquals(t.getTime(), "12:00:02.00");
		t.setTime(null);
		assertEquals(t.getTime(), "12:00:02.00");
		t.setTime("12:00");
		assertEquals(t.getTime(), "12:00:02.00");
	}

	@Test
	public void testGetDifference() {
		String t1 = "12:01:02.0";
		String t2 = "12:01:04.0";
		assertEquals(2, Timer.getDifference(t1, t2), 0);
		assertEquals(0, Timer.getDifference(t2, t1), 0);
		assertEquals(0, Timer.getDifference(t1, t1), 0);

		t2 = "12:15:02.0";
		assertEquals(840, Timer.getDifference(t1, t2), 0);
		t2 = "13:01:02.0";
		assertEquals(3600, Timer.getDifference(t1, t2), 0);
		t1 = "00:00:00.0";
		t2 = "23:59:59.00";
		assertEquals(86399, Timer.getDifference(t1, t2), 0);
		t2 = "23:59:59.9";
		assertEquals(86399.9, Timer.getDifference(t1, t2), 0);
		t2 = "23:59:59.99";
		assertEquals(86399.99, Timer.getDifference(t1, t2), 0);
		t2 = "23:59:59.999";
		assertEquals(86399.999, Timer.getDifference(t1, t2), 0);
		t2 = "13:01"; // invalid time
		assertEquals(0, Timer.getDifference(t1, t2), 0);
		assertEquals(0, Timer.getDifference(null, null), 0);
		assertEquals(0, Timer.getDifference(null, t1), 0);
		assertEquals(0, Timer.getDifference(t1, null), 0);
	}

	@Test
	public void testValidateTime() {
		assertFalse(Timer.validateTime(null));
		assertFalse(Timer.validateTime("10:00"));
		assertFalse(Timer.validateTime("10:00:0"));
		assertFalse(Timer.validateTime("10:00:00 AM"));
		assertFalse(Timer.validateTime("IO:OO:OO")); // letters
		assertFalse(Timer.validateTime("ten"));

		assertFalse(Timer.validateTime("-1:-1:-1.00"));
		assertTrue(Timer.validateTime("00:00:00.0"));
		assertTrue(Timer.validateTime("00:00:00.00"));
		assertTrue(Timer.validateTime("00:00:00.01"));
		assertTrue(Timer.validateTime("00:00:01.00"));
		assertTrue(Timer.validateTime("00:01:00.00"));
		assertTrue(Timer.validateTime("01:00:00.00"));
		assertTrue(Timer.validateTime("14:15:16.17"));
		assertTrue(Timer.validateTime("14:15:16.2"));
		assertTrue(Timer.validateTime("23:59:59.99"));
		assertFalse(Timer.validateTime("24:00:00.00"));
		assertFalse(Timer.validateTime("24:00:00.01"));
		assertFalse(Timer.validateTime("24:00:01.00"));
		assertFalse(Timer.validateTime("24:01:00.00"));
		assertFalse(Timer.validateTime("25:00:00.00"));
	}
}
