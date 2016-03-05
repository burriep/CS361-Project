package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import chronotimer.*;

public class TestChronotimer {

	@Test
	public void testPower() {
		ChronoTimer ct = new ChronoTimer();
		assertEquals(false, ct.getPowerState());
		ct.powerOn();
		assertEquals(true, ct.getPowerState());
		ct.powerOff();
		assertEquals(false, ct.getPowerState());
	}

	@Test
	public void testReset() {
		// TODO
	}
}
