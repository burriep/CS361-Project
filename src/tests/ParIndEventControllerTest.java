package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chronotimer.*;

public class ParIndEventControllerTest {
	@Test
	public void testChannelTriggered() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParIndEventController ec = new ParIndEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
	}

	@Test
	public void testAddTwoRacers() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParIndEventController ec = new ParIndEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.channelTriggered(1);
		ec.channelTriggered(3);
		ec.channelTriggered(2);
		ec.channelTriggered(4);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(2, r.getData().size());
		assertEquals(123, r.getData().get(0).getRacer());
		assertEquals(234, r.getData().get(1).getRacer());
	}
	
	@Test
	public void testAddThreeRacers() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParIndEventController ec = new ParIndEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.addRacer(345);
		ec.addRacer(456);
		ec.channelTriggered(1);
		ec.channelTriggered(3);
		ec.channelTriggered(1);
		//456 is still queued for other lane
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.channelTriggered(4);
		ec.channelTriggered(2);
		assertEquals(1, r.getQueuedRacers().size());
		assertEquals(3, r.getData().size());
		assertEquals(123, r.getData().get(0).getRacer());
		assertEquals(234, r.getData().get(1).getRacer());
		assertEquals(345, r.getData().get(2).getRacer());
	}

	@Test
	public void testEndRun() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParIndEventController ec = new ParIndEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.endRun();
	}

	@Test
	public void testGetRunningDisplay() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParIndEventController ec = new ParIndEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.addRacer(345);
		ec.addRacer(456);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.channelTriggered(3);
		assertEquals("345\n456\n" + "\n" + "234 0.0 R \n" + "\n" + "123 0.0 F\n", ec.getRunningDisplay());
	}

	@Test
	public void testClearRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParIndEventController ec = new ParIndEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.channelTriggered(1);
		ec.clearRacer(234);
		ec.channelTriggered(3);
		ec.channelTriggered(2);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
		assertEquals(123, r.getData().get(0).getRacer());
	}


	@Test
	public void testDnfRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParIndEventController ec = new ParIndEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.channelTriggered(1);
		ec.channelTriggered(3);
		ec.channelTriggered(2);
		ec.dnfRacer();
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(2, r.getData().size());
		assertEquals(234, r.getData().get(1).getRacer());
		assertEquals(null, r.getData().get(1).getEndTime());
	}
}
