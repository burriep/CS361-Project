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
		assertEquals(true, ct.printerIsOn());
		ct.powerOff();
	}

	@Test
	public void testEventChangeAfterRun() {
		ChronoTimer ct = new ChronoTimer();
		ct.setEventType(RunType.IND);
		ct.newRun();
		ct.addRacer(234);
		ct.endRun();
		ct.setEventType(RunType.PARIND);
		ct.newRun();
		ct.addRacer(567);
		
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
	
	
	@Test
	public void testSingleInd() {
		ChronoTimer ct = new ChronoTimer();
		ct.powerOn();
		Sensor sens1 = new Sensor(SensorType.PUSH);
		Sensor sens2 = new Sensor(SensorType.PUSH);
		ct.connectButton(sens1, 1);
		ct.connectButton(sens2, 2);
		ct.toggleChannel(1);
		ct.toggleChannel(2);
		ct.setEventType(RunType.IND);
		ct.newRun();
		ct.addRacer(234);
		ct.update(sens1, null);
		ct.update(sens2, null);
		assertEquals(ct.getRuns().get(0).getData().get(0).getRacer(), 234);
		ct.endRun();
	}
	
	@Test
	public void testMultipleInd() {
		ChronoTimer ct = new ChronoTimer();
		ct.powerOn();
		Sensor sens1 = new Sensor(SensorType.PUSH);
		Sensor sens2 = new Sensor(SensorType.PUSH);
		ct.connectButton(sens1, 1);
		ct.connectButton(sens2, 2);
		ct.toggleChannel(1);
		ct.toggleChannel(2);
		ct.setEventType(RunType.IND);
		ct.newRun();
		ct.addRacer(234);
		ct.addRacer(345);
		ct.update(sens1, null);
		ct.update(sens1, null);
		ct.update(sens2, null);
		ct.update(sens2, null);
		assertEquals(ct.getRuns().get(0).getData().get(0).getRacer(), 234);
		assertEquals(ct.getRuns().get(0).getData().get(1).getRacer(), 345);
		ct.endRun();
	}
	
	@Test
	public void TestSingleParInd(){
		//TODO
		ChronoTimer ct = new ChronoTimer();
		ct.setEventType(RunType.PARIND);
		ct.newRun();
		ct.addRacer(234);
		ct.endRun();
	}
	
	@Test
	public void TestMultipleParInd(){
		//TODO
	}
	
	@Test
	public void testSingleGrp(){
		//TODO
		ChronoTimer ct = new ChronoTimer();
		ct.powerOn();
		Sensor sens1 = new Sensor(SensorType.PUSH);
		Sensor sens2 = new Sensor(SensorType.PUSH);
		ct.connectButton(sens1, 1);
		ct.connectButton(sens2, 2);
		ct.toggleChannel(1);
		ct.toggleChannel(2);
		ct.setEventType(RunType.GRP);
		ct.newRun();
		ct.addRacer(234);
		ct.addRacer(345);
		ct.update(sens1, null);
		ct.update(sens1, null);
		ct.update(sens2, null);
		ct.update(sens2, null);
		assertEquals(ct.getRuns().get(0).getData().get(0).getRacer(), 234);
		assertEquals(ct.getRuns().get(0).getData().get(1).getRacer(), 345);
		ct.endRun();
	}
	
	@Test
	public void testMultipleGrp(){
		//TODO
	}
	
	@Test
	public void switchEventMidrace(){
		//TODO
	}
	
	
	
	
}
