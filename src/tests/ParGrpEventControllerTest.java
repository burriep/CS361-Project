package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chronotimer.*;

public class ParGrpEventControllerTest {
	@Test
	public void testChannelTriggered() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
	}

	@Test
	public void testGetRunningDisplay() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.addRacer(345);
		ec.channelTriggered(1);
		ec.channelTriggered(1);
		ec.channelTriggered(3);
		assertEquals("123 0.0 F\n" + "234 0.0 R\n" + "345 0.0 F\n", ec.getRunningDisplay());
	}

	@Test
	public void testAddRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(1);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
		assertEquals(123, r.getData().get(0).getRacer());
		assertEquals(t.getTime(),  r.getData().get(0).getEndTime());
	}

	@Test
	public void testAddEightRacers() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(111);
		ec.addRacer(222);
		ec.addRacer(333);
		ec.addRacer(444);
		ec.addRacer(555);
		ec.addRacer(666);
		ec.addRacer(777);
		ec.addRacer(888);
		ec.channelTriggered(1);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.channelTriggered(3);
		ec.channelTriggered(4);
		ec.channelTriggered(5);
		ec.channelTriggered(6);
		ec.channelTriggered(7);
		ec.channelTriggered(8);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(8, r.getData().size());
		assertEquals(111, r.getData().get(0).getRacer());
		assertEquals(888, r.getData().get(7).getRacer());
	}
	
	@Test
	public void testAddNineRacers() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(111);
		ec.addRacer(222);
		ec.addRacer(333);
		ec.addRacer(444);
		ec.addRacer(555);
		ec.addRacer(666);
		ec.addRacer(777);
		ec.addRacer(888);
		//Shoudn't get queued, already have each lane occupied
		ec.addRacer(999);
		ec.channelTriggered(1);
		ec.channelTriggered(1);
		ec.channelTriggered(2);
		ec.channelTriggered(3);
		ec.channelTriggered(4);
		ec.channelTriggered(5);
		ec.channelTriggered(6);
		ec.channelTriggered(7);
		ec.channelTriggered(8);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(8, r.getData().size());
		assertEquals(111, r.getData().get(0).getRacer());
		assertEquals(888, r.getData().get(7).getRacer());
	}

	@Test
	public void testClearRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.clearRacer(234);
		ec.channelTriggered(1);
		ec.channelTriggered(1);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(1, r.getData().size());
		assertEquals(123, r.getData().get(0).getRacer());
	}


	@Test
	public void testDnfRacer() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(123);
		ec.addRacer(234);
		ec.channelTriggered(1);
		ec.channelTriggered(1);
		assertEquals(0, r.getQueuedRacers().size());
		assertEquals(2, r.getData().size());
		assertEquals(123, r.getData().get(0).getRacer());
		assertEquals(234, r.getData().get(1).getRacer());
		assertEquals(null, r.getData().get(1).getEndTime());
	}

	@Test
	public void testEndRun() {
		Timer t = new Timer("11:00:00.00", false);
		Run r = new Run();
		ParGrpEventController ec = new ParGrpEventController(t, r);
		ec.addRacer(123);
		ec.channelTriggered(1);
		ec.channelTriggered(1);
		ec.endRun();
	}
}
