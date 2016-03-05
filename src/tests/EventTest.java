package tests;

import org.junit.Test;

import chronotimer.*;

public class EventTest {
	// TODO not finished
	@Test
	public void testConstructor() {
		EventType t1 = EventType.IND;
		Event e1 = new Event(t1);
		e1.newRun();

	}

	@Test(expected = Exception.class)
	public void testEndingRunWithNoRunners() {
		EventType t1 = EventType.IND;
		Event e1 = new Event(t1);
		e1.endRun();
	}

	@Test
	public void testEventSetter() {
		EventType t1 = EventType.IND;
		EventType t2 = EventType.GRP;
		Event e1 = new Event(t1);
		e1.setType(t2);
	}
}
