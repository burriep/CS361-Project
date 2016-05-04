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
		// TODO: make test case more thorough
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
		// TODO assertions missing
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
		// TODO: assertions missing
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
		sens1.trigger();
		sens2.trigger();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(1, cr.getData().size());
		assertEquals(234, cr.getData().get(0).getRacer());

		ct.endRun();
		// re-test after ending the run

		cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(1, cr.getData().size());
		assertEquals(234, cr.getData().get(0).getRacer());
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
		sens1.trigger();
		sens1.trigger();
		sens2.trigger();
		sens2.trigger();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(2, cr.getData().size());
		assertEquals(234, cr.getData().get(0).getRacer());
		assertEquals(345, cr.getData().get(1).getRacer());
		ct.endRun();
	}

	@Test
	public void TestSingleParInd() {
		ChronoTimer ct = new ChronoTimer();
		ct.powerOn();
		Sensor sens1 = new Sensor(SensorType.PUSH);
		Sensor sens2 = new Sensor(SensorType.PUSH);
		ct.connectButton(sens1, 1);
		ct.connectButton(sens2, 2);
		ct.toggleChannel(1);
		ct.toggleChannel(2);
		ct.setEventType(RunType.PARIND);
		ct.newRun();
		ct.addRacer(234);
		sens1.trigger();
		sens2.trigger();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(1, cr.getData().size());
		assertEquals(234, cr.getData().get(0).getRacer());
		ct.endRun();
	}

	@Test
	public void TestMultipleParInd() {
		ChronoTimer ct = new ChronoTimer();
		ct.powerOn();
		Sensor sens1 = new Sensor(SensorType.PUSH);
		Sensor sens2 = new Sensor(SensorType.PUSH);
		Sensor sens3 = new Sensor(SensorType.PUSH);
		Sensor sens4 = new Sensor(SensorType.PUSH);
		ct.connectButton(sens1, 1);
		ct.connectButton(sens2, 2);
		ct.connectButton(sens3, 3);
		ct.connectButton(sens4, 4);
		ct.toggleChannel(1);
		ct.toggleChannel(2);
		ct.toggleChannel(3);
		ct.toggleChannel(4);
		ct.setEventType(RunType.PARIND);
		ct.newRun();
		ct.addRacer(234);
		ct.addRacer(345);
		ct.addRacer(456);
		ct.addRacer(567);
		sens1.trigger();
		sens2.trigger();
		sens1.trigger();
		sens3.trigger();
		sens4.trigger();
		sens3.trigger();
		sens2.trigger();
		sens4.trigger();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(4, cr.getData().size());
		assertEquals(234, cr.getData().get(0).getRacer());
		assertEquals(456, cr.getData().get(1).getRacer());
		assertEquals(345, cr.getData().get(2).getRacer());
		assertEquals(567, cr.getData().get(3).getRacer());

		ct.endRun();
	}

	@Test
	public void testSingleGrp() {
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
		ct.addRacer(1);
		sens1.trigger();
		sens2.trigger();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(1, cr.getData().size());
		assertEquals(1, cr.getData().get(0).getRacer());
		ct.endRun();
	}

	@Test
	public void testMultipleGrp() {
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
		ct.addRacer(1);
		ct.addRacer(2);
		ct.addRacer(3);
		ct.addRacer(4);
		sens1.trigger();
		sens1.trigger();
		sens2.trigger();
		sens1.trigger();
		sens2.trigger();
		sens2.trigger();
		sens1.trigger();
		sens2.trigger();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(4, cr.getData().size());
		assertEquals(1, cr.getData().get(0).getRacer());
		assertEquals(2, cr.getData().get(1).getRacer());
		assertEquals(3, cr.getData().get(2).getRacer());
		assertEquals(4, cr.getData().get(3).getRacer());
		ct.endRun();
	}

	@Test
	public void switchEventMidrace() {
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
		sens1.trigger();
		ct.setEventType(RunType.GRP);
		sens2.trigger();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(1, cr.getData().size());
		assertEquals(234, cr.getData().get(0).getRacer());
		ct.endRun();
	}

	@Test
	public void testDidNotFinish() {
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
		sens1.trigger();
		ct.dnfRacer();

		Run cr = ct.getRuns().get(ct.getRuns().size() - 1);
		assertEquals(1, cr.getData().size());
		assertEquals(234, cr.getData().get(0).getRacer());
		assertEquals(null, cr.getData().get(0).getEndTime());
		ct.endRun();
	}
}
