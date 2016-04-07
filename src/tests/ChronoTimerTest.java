package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chronotimer.*;

public class ChronoTimerTest {

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
		ChronoTimer ct = new ChronoTimer();
		ct.powerOn();
		ct.reset();
		assertEquals(false, ct.printerIsOn());
		ct.powerOff();
	}

	@Test
	public void testEventChangeAfterRun() {
		ChronoTimer ct = new ChronoTimer();
		ct.setEventType(RunType.IND);
		ct.newRun();
		ct.addRacer(234);
		ct.endRun();
		ct.setEventType(RunType.IND);

	}

	@Test
	public void testWhileOff() {
		ChronoTimer ct = new ChronoTimer();
		ct.powerOff();
		ct.setEventType(RunType.IND);
		ct.newRun();
		ct.addRacer(234);
		ct.endRun();
		ct.setEventType(RunType.IND);
	}
}
