package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import chronotimer.Sensor;
import chronotimer.SensorType;

public class SensorTest {
	public class TestObserver implements Observer {
		public boolean wasUpdated;

		@Override
		public void update(Observable o, Object arg) {
			wasUpdated = true;
		}
	}

	@Test
	public void testInitialization() {
		Sensor s = new Sensor(null);
		assertEquals(s.getSensorType(), SensorType.PUSH);
		s = new Sensor(SensorType.EYE);
		assertEquals(s.getSensorType(), SensorType.EYE);
		// check that getSensorType() doesn't change the sensor type
		assertEquals(s.getSensorType(), SensorType.EYE);
		s = new Sensor(SensorType.GATE);
		assertEquals(s.getSensorType(), SensorType.GATE);
		s = new Sensor(SensorType.PAD);
		assertEquals(s.getSensorType(), SensorType.PAD);
		s = new Sensor(SensorType.PUSH);
		assertEquals(s.getSensorType(), SensorType.PUSH);
	}

	@Test
	public void testTrigger() {
		// setup
		Sensor s = new Sensor(SensorType.GATE);
		TestObserver to = new TestObserver();
		// check that it is defaulted to false
		assertFalse(to.wasUpdated);
		s.addObserver(to);
		// check that adding an observer didn't trigger an update
		assertFalse(to.wasUpdated);
		// check that the observer was added
		assertEquals(s.countObservers(), 1);
		s.trigger();
		// check that the observer was updated
		assertTrue(to.wasUpdated);
		// reset observer state
		to.wasUpdated = false;
		// check that we still receive updates and weren't removed.
		s.trigger();
		assertTrue(to.wasUpdated);
		to.wasUpdated = false;
		// check that a removed observer does not get updated
		s.deleteObserver(to);
		assertEquals(s.countObservers(), 0);
		s.trigger();
		assertFalse(to.wasUpdated);
		// check that we can re-add the observer
		s.addObserver(to);
		assertEquals(s.countObservers(), 1);
		s.trigger();
		assertTrue(to.wasUpdated);
	}
}
