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
		assertEquals(EventType.IND, ct.getEvents().get(0).getType());
		ct.powerOff();
	}
	
	@Test
	public void testEventChangeAfterRun(){
		ChronoTimer ct = new ChronoTimer();
		Event e1 = new Event(EventType.IND);
		Event e2 = new Event(EventType.PARIND);
		Racer r1 = new Racer(234);
		ct.newEvent(e1);
		ct.newRunCurrentEvent();
		ct.addRacerToCurrentRun(r1);		
	}
	
}
