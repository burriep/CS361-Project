package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import chronotimer.*;

public class EventTest {
	// TODO not finished
	@Test
	public void testConstructor() {
		EventType t1 = EventType.IND;
		Event e1 = new Event(t1);
		assertEquals(EventType.IND, e1.getType());
		e1.newRun();

	}

	@Test(expected = Exception.class)
	public void testEndingRunWithNoRunners() {
		EventType t1 = EventType.IND;
		Event e1 = new Event(t1);
		e1.endRun();
		assertEquals(false, e1.isActive());
	}

	@Test
	public void testEventSetter() {
		EventType t1 = EventType.IND;
		EventType t2 = EventType.PARIND;
		Event e1 = new Event(t1);
		assertEquals(EventType.IND, e1.getType());
		e1.setType(t2);
		assertEquals(EventType.PARIND, e1.getType());
	}
}
