package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chronotimer.*;

public class RunTest {

	@Test
	public void testRun() {
		int r1 = 123;
		int r2 = 215;

		Run run = new Run();
		run.addRacer(r1);
		run.addRacer(r2);
		assertEquals(2, run.getQueuedRacers().size());
		run.clearRacer(r1);
		assertEquals(1, run.getQueuedRacers().size());
		int r3 = run.getQueuedRacers().iterator().next();
		assertEquals(r2, r3);
	}

	@Test
	public void testMultipleRuns() {
		int r1 = 123;
		int r2 = 215;
		int r3 = 275;

		Run run = new Run();
		run.addRacer(r1);
		run.addRacer(r2);
		run.addRacer(r3);
		assertEquals(3, run.getQueuedRacers().size());
		int r4 = run.getQueuedRacers().iterator().next();
		assertEquals(r1, r4);
		run.clearRacer(r2);
		assertEquals(r1, r4);
		run.clearRacer(r1);
		assertEquals(r3, 275);
	}

	@Test(expected = Exception.class)
	public void testZeroRunners() {
		Run run = new Run();
		int r1 = 1;
		run.clearRacer(r1);
	}
	
	@Test
	public void testSwapRacers(){
		ChronoTimer ct = new ChronoTimer();
		int r1 = 123;
		int r2 = 215;

		Run run = new Run();
		run.addRacer(r1);
		run.addRacer(r2);
		run.addRacerStartTime(ct.getTimer().getTime());
		run.addRacerStartTime(ct.getTimer().getTime());
		run.swapRacer();
		
		assertEquals(run.getData().get(0).getRacer(), r2);
		assertEquals(run.getData().get(1).getRacer(), r1);
	}

}
