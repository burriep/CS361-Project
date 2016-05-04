package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chronotimer.*;

public class RunTest {
	@Test
	public void testInitialization() {
		Run r = new Run();
		assertTrue(r.getQueuedRacers().isEmpty());
		assertTrue(r.getStartedRacers().isEmpty());
		assertTrue(r.getData().isEmpty());
		assertTrue(r.isActive());
	}

	@Test
	public void testSetActive() {
		Run r = new Run();
		assertTrue(r.isActive());
		r.setActive(false);
		assertFalse(r.isActive());
		r.setActive(true);
		assertTrue(r.isActive());
	}

	@Test
	public void testAddRacerID() {
		Run r = new Run();
		int racerID[] = { 123, 215 };
		r.addRacer(racerID[0]);
		assertEquals(1, r.getQueuedRacers().size());
		assertTrue(r.getStartedRacers().isEmpty());
		assertTrue(r.getData().isEmpty());
		assertEquals(racerID[0], (int) r.getQueuedRacers().get(0));
		// add a second racer and ensure that it is added after the first one.
		r.addRacer(racerID[1]);
		assertEquals(2, r.getQueuedRacers().size());
		assertTrue(r.getStartedRacers().isEmpty());
		assertTrue(r.getData().isEmpty());
		assertEquals(racerID[0], (int) r.getQueuedRacers().get(0));
		assertEquals(racerID[1], (int) r.getQueuedRacers().get(1));
	}

	@Test
	public void testAddRacerIDStart() {
		Run r = new Run();
		int racerID[] = { 123, 215 };
		Time t[] = { Timer.convertToTime("11:00:00.00"), Timer.convertToTime("11:00:05.00") };
		r.addRacer(racerID[0], t[0]);
		assertTrue(r.getQueuedRacers().isEmpty());
		assertEquals(1, r.getStartedRacers().size());
		assertEquals(1, r.getData().size());
		assertEquals(racerID[0], (int) r.getStartedRacers().get(0));
		assertEquals(racerID[0], (int) r.getData().get(0).getRacer());
		assertEquals(t[0], r.getData().get(0).getStartTime());
		assertEquals(null, r.getData().get(0).getEndTime());
		// add a second racer and ensure that it is added after the first one.
		r.addRacer(racerID[1], t[1]);
		assertTrue(r.getQueuedRacers().isEmpty());
		assertEquals(2, r.getStartedRacers().size());
		assertEquals(2, r.getData().size());
		assertEquals(racerID[0], (int) r.getStartedRacers().get(0));
		assertEquals(racerID[1], (int) r.getStartedRacers().get(1));
		assertEquals(racerID[0], (int) r.getData().get(0).getRacer());
		assertEquals(racerID[1], (int) r.getData().get(1).getRacer());
		assertEquals(t[0], r.getData().get(0).getStartTime());
		assertEquals(t[1], r.getData().get(1).getStartTime());
		assertEquals(null, r.getData().get(0).getEndTime());
		assertEquals(null, r.getData().get(1).getEndTime());
	}

	@Test
	public void testAddRacerIDStartEnd() {
		Run r = new Run();
		int racerID[] = { 123, 215 };
		Time t[] = { Timer.convertToTime("11:00:00.00"), Timer.convertToTime("11:00:05.00"),
				Timer.convertToTime("11:01:00.00"), Timer.convertToTime("11:02:05.00") };
		r.addRacer(racerID[0], t[0], t[2]);
		assertTrue(r.getQueuedRacers().isEmpty());
		assertTrue(r.getStartedRacers().isEmpty());
		assertEquals(1, r.getData().size());
		assertEquals(racerID[0], (int) r.getData().get(0).getRacer());
		assertEquals(t[0], r.getData().get(0).getStartTime());
		assertEquals(t[2], r.getData().get(0).getEndTime());
		// add a second racer and ensure that it is added after the first one.
		r.addRacer(racerID[1], t[1], t[3]);
		assertTrue(r.getQueuedRacers().isEmpty());
		assertTrue(r.getStartedRacers().isEmpty());
		assertEquals(2, r.getData().size());
		assertEquals(racerID[0], (int) r.getData().get(0).getRacer());
		assertEquals(racerID[1], (int) r.getData().get(1).getRacer());
		assertEquals(t[0], r.getData().get(0).getStartTime());
		assertEquals(t[1], r.getData().get(1).getStartTime());
		assertEquals(t[2], r.getData().get(0).getEndTime());
		assertEquals(t[3], r.getData().get(1).getEndTime());
	}

	@Test
	public void testAddRacerBounds() {
		// test 1 argument method
		// test add -1
		Run r = new Run();
		r.addRacer(-1);
		assertTrue(r.getQueuedRacers().isEmpty());
		// test add 0
		r = new Run();
		r.addRacer(0);
		assertEquals(1, r.getQueuedRacers().size());
		// test add 550
		r = new Run();
		r.addRacer(550);
		assertEquals(1, r.getQueuedRacers().size());
		// test add 99999
		r = new Run();
		r.addRacer(99999);
		assertEquals(1, r.getQueuedRacers().size());
		// test add 100000
		r = new Run();
		r.addRacer(100000);
		assertTrue(r.getQueuedRacers().isEmpty());

		// test 2 argument method
		// test add -1
		r = new Run();
		Time t1 = Timer.convertToTime("11:00:00.00");
		r.addRacer(-1, t1);
		assertTrue(r.getStartedRacers().isEmpty());
		// test add 0
		r = new Run();
		r.addRacer(0, t1);
		assertEquals(1, r.getStartedRacers().size());
		// test add 550
		r = new Run();
		r.addRacer(550, t1);
		assertEquals(1, r.getStartedRacers().size());
		// test add 99999
		r = new Run();
		r.addRacer(99999, t1);
		assertEquals(1, r.getStartedRacers().size());
		// test add 100000
		r = new Run();
		r.addRacer(100000, t1);
		assertTrue(r.getStartedRacers().isEmpty());

		// test 3 argument method
		// test add -1
		r = new Run();
		Time t2 = Timer.convertToTime("11:02:00.00");
		r.addRacer(-1, t2, t1); // end time > start time
		assertTrue(r.getData().isEmpty());
		r.addRacer(-1, t1, t2);
		assertTrue(r.getData().isEmpty());
		// test add 0
		r = new Run();
		r.addRacer(0, t2, t1); // end time > start time
		assertTrue(r.getData().isEmpty());
		r.addRacer(0, t1, t2);
		assertEquals(1, r.getData().size());
		// test add 550
		r = new Run();
		r.addRacer(550, t2, t1); // end time > start time
		assertTrue(r.getData().isEmpty());
		r.addRacer(550, t1, t1);
		assertEquals(1, r.getData().size());
		r = new Run();
		r.addRacer(550, t1, t2);
		assertEquals(1, r.getData().size());
		// test add 99999
		r = new Run();
		r.addRacer(99999, t2, t1); // end time > start time
		assertTrue(r.getData().isEmpty());
		r.addRacer(99999, t1, t2);
		assertEquals(1, r.getData().size());
		// test add 100000
		r = new Run();
		r.addRacer(100000, t2, t1); // end time > start time
		assertTrue(r.getData().isEmpty());
		r.addRacer(100000, t1, t2);
		assertTrue(r.getData().isEmpty());
	}

	@Test
	public void testClearRacer() {
		Run r = new Run();
		// Test with empty run
		r.clearRacer(1);
		assertTrue(r.getQueuedRacers().isEmpty());
		// test with 1 racer (which matches) in run
		r.addRacer(111);
		r.clearRacer(111);
		assertTrue(r.getQueuedRacers().isEmpty());
		r.addRacer(222);
		r.clearRacer(111);
		assertEquals(1, r.getQueuedRacers().size());
		// test with 2 racers (one of which matches)
		r.addRacer(333);
		r.clearRacer(222);
		assertEquals(1, r.getQueuedRacers().size());
	}

	@Test
	public void testDnfRacer() {
		// TODO
	}

	@Test
	public void testAddRacerStartTime() {
		// TODO
	}

	@Test
	public void testAddRacerEndTime() {
		// TODO
	}

	@Test
	public void testSwapRacers1() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run(); // (a run has no racers by default)
		int racerID[] = { 123, 654, 741, 357, 111, 789, 321, 888, 999 };

		// test swap with 1 racer
		r.addRacer(racerID[0]);
		Time t1 = t.getTime(); // 11:00:00.00
		r.addRacerStartTime(t1); // start
		r.swapRacer();
		assertTrue(r.getQueuedRacers().isEmpty());
		assertEquals(1, r.getStartedRacers().size());
		assertEquals(racerID[0], (int) r.getStartedRacers().get(0));
		assertEquals(1, r.getData().size());
		assertEquals(racerID[0], r.getData().get(0).getRacer());
		assertEquals(t1, r.getData().get(0).getStartTime());
	}

	@Test
	public void testSwapRacers2() {
		Timer t = new Timer("11:00:00.00", false);
		Time t1 = t.getTime(); // 11:00:00.00
		t.setTime("11:00:05.00");
		Time t2 = t.getTime(); // 11:00:05.00
		Run r = new Run(); // (a run has no racers by default)
		int racerID[] = { 123, 654, 741, 357, 111, 789, 321, 888, 999 };

		// test swap with 2 racers
		r = new Run();
		r.addRacer(racerID[0]);
		r.addRacer(racerID[1]);

		r.addRacerStartTime(t1); // start
		r.addRacerStartTime(t2);
		r.setActive(false);
		r.swapRacer(); // should not do anything, race is not active
		assertTrue(r.getQueuedRacers().isEmpty());
		assertEquals(2, r.getStartedRacers().size());
		assertEquals(racerID[0], (int) r.getStartedRacers().get(0));
		assertEquals(racerID[1], (int) r.getStartedRacers().get(1));
		assertEquals(2, r.getData().size());
		assertEquals(racerID[0], r.getData().get(0).getRacer());
		assertEquals(racerID[1], r.getData().get(1).getRacer());
		assertEquals(t1, r.getData().get(0).getStartTime());
		assertEquals(t2, r.getData().get(1).getStartTime());

		r.setActive(true);
		r.swapRacer(); // should swap racers since race is active
		assertTrue(r.getQueuedRacers().isEmpty());
		assertEquals(2, r.getStartedRacers().size());
		assertEquals(racerID[1], (int) r.getStartedRacers().get(0));
		assertEquals(racerID[0], (int) r.getStartedRacers().get(1));
		assertEquals(2, r.getData().size());
		assertEquals(racerID[1], r.getData().get(0).getRacer());
		assertEquals(racerID[0], r.getData().get(1).getRacer());
		assertEquals(t2, r.getData().get(0).getStartTime());
		assertEquals(t1, r.getData().get(1).getStartTime());
	}

	@Test
	public void testSwapRacers3() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run(); // (a run has no racers by default)
		int racerID[] = { 123, 654, 741, 357, 111, 789, 321, 888, 999 };

		// test swap with 3 racers queued, 3 racers started, 3 racers queued
		r = new Run();
		for (int i = 0; i < racerID.length; i++) {
			r.addRacer(racerID[i]);
		}
		// 6 racers start
		Time starts[] = new Time[racerID.length];
		for (int i = 0; i < 6; i++) {
			t.setTime("10:00:0" + i + ".00");
			starts[i] = t.getTime();
			r.addRacerStartTime(starts[i]); // start
		}
		// 3 racers end, each with different end times & durations
		Time ends[] = new Time[racerID.length];
		for (int i = 0; i < 3; i++) {
			t.setTime("10:02:0" + (i + i) + ".00");
			ends[i] = t.getTime();
			r.addRacerEndTime(ends[i]); // end
		}

		// ended | started | queued
		// 0 1 2 | 3 4 5 | 6 7 8
		r.swapRacer();
		// ended | started | queued
		// 0 1 2 | 4 3 5 | 6 7 8

		assertEquals(3, r.getQueuedRacers().size());
		// last 3 racers should be in the queue, not started, in same order
		for (int i = 0; i < 3; i++) {
			assertEquals(racerID[i + 6], (int) r.getQueuedRacers().get(i));
		}
		assertEquals(3, r.getStartedRacers().size());
		// middle 3 racers should be started, index 3 & 4 should be swapped
		assertEquals(racerID[4], (int) r.getStartedRacers().get(0));
		assertEquals(racerID[3], (int) r.getStartedRacers().get(1));
		assertEquals(racerID[5], (int) r.getStartedRacers().get(2));

		// finished racers should not be swapped
		assertEquals(6, r.getData().size());
		assertEquals(racerID[0], r.getData().get(0).getRacer());
		assertEquals(racerID[1], r.getData().get(1).getRacer());
		assertEquals(racerID[2], r.getData().get(2).getRacer());

		// started racer data should be swapped
		assertEquals(racerID[4], r.getData().get(3).getRacer());
		assertEquals(racerID[3], r.getData().get(4).getRacer());
		assertEquals(racerID[5], r.getData().get(5).getRacer());

		// check start times
		assertEquals(starts[0], r.getData().get(0).getStartTime());
		assertEquals(starts[1], r.getData().get(1).getStartTime());
		assertEquals(starts[2], r.getData().get(2).getStartTime());
		assertEquals(starts[4], r.getData().get(3).getStartTime());
		assertEquals(starts[3], r.getData().get(4).getStartTime());
		assertEquals(starts[5], r.getData().get(5).getStartTime());
		// check finish times
		assertEquals(ends[0], r.getData().get(0).getEndTime());
		assertEquals(ends[1], r.getData().get(1).getEndTime());
		assertEquals(ends[2], r.getData().get(2).getEndTime());

		// ended | started | queued
		// 0 1 2 | 4 3 5 | 6 7 8
		r.dnfRacer(); // DNF racerID[4]
		r.swapRacer();
		// ended | started | queued
		// 0 1 2 4 | 5 3 | 6 7 8

		// last 3 racers should be in the queue, not started, in same order
		for (int i = 0; i < 3; i++) {
			assertEquals(racerID[i + 6], (int) r.getQueuedRacers().get(i));
		}
		assertEquals(2, r.getStartedRacers().size());
		// 2 racers should be started - racerID[5] and racerID[3]
		assertEquals(racerID[5], (int) r.getStartedRacers().get(0));
		assertEquals(racerID[3], (int) r.getStartedRacers().get(1));

		// finished racers should not be swapped
		assertEquals(6, r.getData().size());
		assertEquals(racerID[0], r.getData().get(0).getRacer());
		assertEquals(racerID[1], r.getData().get(1).getRacer());
		assertEquals(racerID[2], r.getData().get(2).getRacer());
		assertEquals(racerID[4], r.getData().get(3).getRacer());

		// started racer data should be swapped
		assertEquals(racerID[5], r.getData().get(4).getRacer());
		assertEquals(racerID[3], r.getData().get(5).getRacer());

		// check start times
		assertEquals(starts[0], r.getData().get(0).getStartTime());
		assertEquals(starts[1], r.getData().get(1).getStartTime());
		assertEquals(starts[2], r.getData().get(2).getStartTime());
		assertEquals(starts[4], r.getData().get(3).getStartTime());
		assertEquals(starts[5], r.getData().get(4).getStartTime());
		assertEquals(starts[3], r.getData().get(5).getStartTime());
		// check finish times
		assertEquals(ends[0], r.getData().get(0).getEndTime());
		assertEquals(ends[1], r.getData().get(1).getEndTime());
		assertEquals(ends[2], r.getData().get(2).getEndTime());
		assertEquals(null, r.getData().get(3).getEndTime());
	}
}
