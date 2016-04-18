package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chronotimer.Timer;

public class TimerTest {
	@Test
	public void testInitialization() {
		// zero argument constructor
		Timer t = new Timer();
		assertFalse(t.getTime() == null);
		// one argument constructor
		t = new Timer(null);
		assertFalse(t.getTimeString() == null);
		t = new Timer("11:00");
		assertFalse(t.getTimeString() == null);
		assertFalse(t.getTimeString() == "11:00");
		assertFalse(t.getTimeString() == "11:00:00.00");
		t = new Timer("11:00:00.00");
		assertEquals(t.getTimeString(), "11:00:00.00");
	}

	@Test
	public void testSetTime() {
		Timer t = new Timer("11:00:00.00");
		assertEquals(t.getTimeString(), "11:00:00.00");
		t.setTime("12:00:01.00");
		assertEquals(t.getTimeString(), "12:00:01.00");
		t.setTime("12:00:02.00");
		assertEquals(t.getTimeString(), "12:00:02.00");
		t.setTime(null);
		assertEquals(t.getTimeString(), "12:00:02.00");
		t.setTime("12:00");
		assertEquals(t.getTimeString(), "12:00:02.00");
	}

	@Test
	public void testRealTimeSetTime() throws InterruptedException {
		String st0 = "18:00:00.00", st1, st2;
		// start a timer with real-time off
		Timer t = new Timer(true);
		Thread.sleep(500);
		t.setTime(st0);
		st1 = t.getTimeString();
		// might be a few milliseconds off
		assertEquals(0, Timer.getDifference(st0, st1), 0.1);
		Thread.sleep(500);
		st2 = t.getTimeString();
		// might be a few milliseconds off
		assertEquals(0.5, Timer.getDifference(st1, st2), 0.1);
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
		// assertEquals(0, Timer.getDifference(null, null), 0);
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

	@Test
	public void testRealTime() throws InterruptedException {
		String ts0 = "11:00:00.00", ts1;
		Timer t = new Timer(ts0, true);
		// wait a second
		Thread.sleep(1000);
		ts1 = t.getTimeString();
		assertFalse(ts0.equals(ts1));
		// might be a few milliseconds off
		assertEquals(1, Timer.getDifference(ts0, ts1), 0.1);
	}

	@Test
	public void testRealTimeStartStop() throws InterruptedException {
		String st0 = "02:00:00.00", st1, st2;
		// start a timer with real-time off
		Timer t = new Timer(st0, false);
		assertEquals(st0, t.getTimeString());
		Thread.sleep(500);
		assertEquals(st0, t.getTimeString());
		t.start();
		Thread.sleep(1000);
		st1 = t.getTimeString();
		assertFalse(st0.equals(st1));
		// might be a few milliseconds off
		assertEquals(1, Timer.getDifference(st0, st1), 0.1);
		// test double start
		st1 = t.getTimeString();
		t.start();
		Thread.sleep(1000);
		st2 = t.getTimeString();
		assertFalse(st2.equals(st1));
		// might be a few milliseconds off
		assertEquals(1, Timer.getDifference(st1, st2), 0.1);

		// test stop
		t.stop();
		st1 = t.getTimeString();
		Thread.sleep(1000);
		st2 = t.getTimeString();
		assertEquals(st1, st2);
		// test double stop
		t.stop();
		assertEquals(t.getTimeString(), st2);
	}
}
