package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chronotimer.*;

public class IndEventControllerTest {
	@Test
	public void testChannelTriggered() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		IndEventController ec = new IndEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
	}

	@Test
	public void testSwapRacer() {
		// Simple test to verify that swap is implemented.
		// More detailed test of swap in the RunTests.
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		IndEventController ec = new IndEventController(t, r);
		int racerID[] = { 123, 654, 741 };
		// test swap with 2 racers
		ec.addRacer(racerID[0]);
		ec.addRacer(racerID[1]);
		ec.channelTriggered(1); // start
		ec.channelTriggered(1); // start
		ec.swapRacer();
		assertTrue(r.getQueuedRacers().isEmpty());
		assertEquals(2, r.getStartedRacers().size());
		assertEquals(2, r.getData().size());
		assertEquals(racerID[1], r.getData().get(0).getRacer());
		assertEquals(racerID[0], r.getData().get(1).getRacer());
	}

	@Test
	public void testAddRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		IndEventController ec = new IndEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
		assertEquals(123, r.getData().get(0).getRacer());
	}

	@Test
	public void testClearRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		IndEventController ec = new IndEventController(t, r);
		ec.addRacer(123);
		ec.clearRacer(123);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(0, r.getData().size());
	}

	@Test
	public void testDnfRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		IndEventController ec = new IndEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.dnfRacer();
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(123, r.getData().get(0).getRacer());
		assertEquals(1, r.getData().size());
		assertEquals(null, r.getData().get(0).getEndTime());
	}

	@Test
	public void testEndRun() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		IndEventController ec = new IndEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.endRun();
	}
}
