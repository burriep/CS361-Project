package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import chronotimer.*;

public class TestChronotimer {

	@Test
	public void testPower() {
		ChronoTimer ct = new ChronoTimer();
		assertEquals(false, ct.isOn());
		ct.powerOn();
		assertEquals(true, ct.isOn());
		ct.powerOff();
		assertEquals(false, ct.isOn());
	}

	@Test
	public void testReset() {
		// TODO
	}
}
