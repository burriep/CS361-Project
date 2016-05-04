package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chronotimer.*;

public class GrpEventControllerTest {
	@Test
	public void testChannelTriggered() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		GrpEventController ec = new GrpEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
		assertEquals(t.getTime(), r.getData().get(0).getStartTime());
		assertEquals(t.getTime(), r.getData().get(0).getEndTime());
	}

	@Test
	public void testAddRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		GrpEventController ec = new GrpEventController(t, r);
		ec.addRacer(1);
		ec.addRacer(2);
		ec.addRacer(3);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.channelTriggered(2);
		ec.channelTriggered(2);
		assertEquals(3, r.getData().size());
		assertEquals(1, r.getData().get(0).getRacer());
		assertEquals(2, r.getData().get(1).getRacer());
		assertEquals(3, r.getData().get(2).getRacer());
	}

	@Test
	public void testEndRun() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		GrpEventController ec = new GrpEventController(t, r);
		ec.addRacer(1);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.endRun();
	}

	@Test
	public void testGetRunningDisplay() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		GrpEventController ec = new GrpEventController(t, r);
		ec.addRacer(1);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		assertEquals("0.0\n1 0.0 F\n", ec.getRunningDisplay());
	}


	@Test
	public void testClearRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		GrpEventController ec = new GrpEventController(t, r);
		ec.addRacer(123);
		ec.clearRacer(123);
		assertEquals(0, r.getQueuedRacers().size());
	}
}
