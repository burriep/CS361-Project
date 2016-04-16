package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import chronotimer.RacerRun;
import chronotimer.Time;
import chronotimer.Timer;

public class RacerRunTest {
	@Test
	public void testConstructorBounds() {
		int racerID;
		RacerRun rr = null;
		try {
			racerID = -2; // invalid racerID
			rr = new RacerRun(racerID);
			fail(racerID + " as a racer ID should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(rr == null); // rr was never assigned
		}
		try {
			rr = null;
			racerID = -1; // lower invalid boundary
			rr = new RacerRun(racerID);
			fail(racerID + " as a racer ID should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(rr == null); // rr was never assigned
		}

		racerID = 0; // lower valid boundary
		rr = new RacerRun(racerID);
		assertEquals(racerID, rr.getRacer());

		racerID = 3141; // ID somewhere in the middle
		rr = new RacerRun(racerID);
		assertEquals(racerID, rr.getRacer());

		racerID = 99999; // upper valid boundary
		rr = new RacerRun(racerID);
		assertEquals(racerID, rr.getRacer());

		try {
			rr = null;
			racerID = 100000; // upper invalid boundary
			rr = new RacerRun(racerID);
			fail(racerID + " as a racer ID should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(rr == null); // rr was never assigned
		}
		try {
			rr = null;
			racerID = 111111; // invalid racerID
			rr = new RacerRun(racerID);
			fail(racerID + " as a racer ID should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(rr == null); // rr was never assigned
		}

		// test 2 argument constructor:
		Time t1 = null;
		racerID = 3141;
		rr = new RacerRun(racerID, t1);
		assertEquals(racerID, rr.getRacer());
		assertEquals(t1, rr.getStartTime());

		t1 = Timer.convertToTime("12:00:00.00");
		racerID = 3141;
		rr = new RacerRun(racerID, t1);
		assertEquals(racerID, rr.getRacer());
		assertEquals(t1, rr.getStartTime());

		// test 3 argument constructor
		t1 = null;
		Time t2 = Timer.convertToTime("12:01:00.00");
		racerID = 3141;
		rr = new RacerRun(racerID, t1, t2);
		assertEquals(racerID, rr.getRacer());
		assertEquals(t1, rr.getStartTime());
		assertEquals(t2, rr.getEndTime());

		// t1 < t2
		t1 = Timer.convertToTime("14:00:00.00");
		t2 = Timer.convertToTime("14:02:00.00");
		racerID = 3141;
		rr = new RacerRun(racerID, t1, t2);
		assertEquals(racerID, rr.getRacer());
		assertEquals(t1, rr.getStartTime());
		assertEquals(t2, rr.getEndTime());

		// t1 == t2
		t1 = Timer.convertToTime("12:00:00.00");
		t2 = (Time) t1.clone();
		racerID = 3141;
		rr = new RacerRun(racerID, t1, t2);
		assertEquals(racerID, rr.getRacer());
		assertEquals(t1, rr.getStartTime());
		assertEquals(t2, rr.getEndTime());

		// t1 > t2
		try {
			rr = null;
			racerID = 3141; // invalid racerID
			t1 = Timer.convertToTime("12:05:00.00");
			t2 = Timer.convertToTime("12:00:00.00");
			rr = new RacerRun(racerID, t1, t2);
			fail("Constructor should have thrown IllegalArgumentException for startTime > endTime");
		} catch (IllegalArgumentException e) {
			assertTrue(rr == null); // rr was never assigned
		}
	}

	@Test
	public void testAccessors() {
		Time t1 = Timer.convertToTime("14:00:00.00");
		Time t2 = Timer.convertToTime("14:01:50.00");
		int racerID = 3141;
		RacerRun rr = new RacerRun(racerID, t1, t2);
		assertEquals(racerID, rr.getRacer());
		assertEquals(t1, rr.getStartTime());
		assertEquals(t2, rr.getEndTime());
	}

	@Test
	public void testSetRacer() {
		int rID1 = 11;
		int rID2 = rID1;
		RacerRun rr = new RacerRun(rID1);

		rID2 = -2; // racer ID below lower boundary
		rr.setRacer(rID2);
		assertEquals(rID1, rr.getRacer());

		rID2 = -1; // lower invalid boundary
		rr.setRacer(rID2);
		assertEquals(rID1, rr.getRacer());

		rID2 = 0; // lower valid boundary
		rr.setRacer(rID2);
		assertEquals(rID2, rr.getRacer());

		rID2 = 22; // valid ID
		rr.setRacer(rID2);
		assertEquals(rID2, rr.getRacer());

		rID2 = 99999; // upper valid ID
		rr.setRacer(rID2);
		assertEquals(rID2, rr.getRacer());

		rID1 = 100000; // upper invalid boundary
		rr.setRacer(rID1);
		assertEquals(rID2, rr.getRacer());

		rID1 = 100501; // upper invalid boundary
		rr.setRacer(rID1);
		assertEquals(rID2, rr.getRacer());
	}

	@Test
	public void testSetStartTime() {
		Time t1 = Timer.convertToTime("14:01:50.00");
		Time t2 = Timer.convertToTime("14:03:01.01");
		Time t3 = Timer.convertToTime("15:00:00.49");
		int racerID = 3141;
		RacerRun rr = new RacerRun(racerID, t1, t2);

		rr.setStartTime(null); // remove start time
		assertEquals(null, rr.getStartTime());

		rr.setStartTime(t1); // start < end
		assertEquals(t1, rr.getStartTime());

		rr.setStartTime(t2); // start == end
		assertEquals(t2, rr.getStartTime());

		rr.setStartTime(t3); // start > end
		assertEquals(t2, rr.getStartTime());
	}

	@Test
	public void testSetEndTime() {
		Time t1 = Timer.convertToTime("14:00:00.00");
		Time t2 = Timer.convertToTime("14:01:50.00");
		Time t3 = Timer.convertToTime("14:03:01.01");
		int racerID = 3141;
		RacerRun rr = new RacerRun(racerID, t2, t3);

		rr.setEndTime(null); // remove end time
		assertEquals(null, rr.getEndTime());

		rr.setEndTime(t3); // start < end
		assertEquals(t3, rr.getEndTime());

		rr.setEndTime(t2); // start == end
		assertEquals(t2, rr.getEndTime());

		rr.setEndTime(t1); // start > end
		assertEquals(t2, rr.getEndTime());
	}

	@Test
	public void testGetElapsedTime() {
		Time t1 = Timer.convertToTime("14:00:00.00");
		Time t2 = Timer.convertToTime("14:00:50.00");
		int racerID = 3141;
		RacerRun rr = new RacerRun(racerID, t1, t2);
		assertEquals(50, rr.getElapsedTime(), 0);

		rr = new RacerRun(racerID); // no start or end time
		assertEquals(0, rr.getElapsedTime(), 0);

		rr = new RacerRun(racerID, null, t2); // no start time
		assertEquals(0, rr.getElapsedTime(), 0);

		rr = new RacerRun(racerID, t1); // no end time
		assertEquals(0, rr.getElapsedTime(), 0);
	}
}
