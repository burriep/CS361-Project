package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chronotimer.*;

public class RunTest {

	@Test
	public void testRun() {
		Racer r1 = new Racer(123);
		Racer r2 = new Racer(215);

		Run run = new Run();
		run.addRacer(r1);
		run.addRacer(r2);
		assertEquals(2, run.getQueuedRacers().size());
		run.clearRacer(r1);
		assertEquals(1, run.getQueuedRacers().size());
		Racer r3 = run.getQueuedRacers().iterator().next();
		assertEquals(r2, r3);
		assertEquals(r2.getNumber(), r3.getNumber());
	}

	@Test(expected = Exception.class)
	public void testZeroRunners() {
		Run run = new Run();
		Racer r1 = new Racer(1);
		run.clearRacer(r1);
	}

}
