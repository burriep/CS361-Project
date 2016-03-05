package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chronotimer.*;

public class TestPrinter {

	@Test
	public void testIntialPrinterState() {
		Printer p = new Printer();
		assertFalse(p.isOn());
	}

	@Test
	public void testPrinterState() {
		Printer p = new Printer();
		p.powerOn();
		assertTrue(p.isOn());
		p.powerOff();
		assertFalse(p.isOn());

	}

	@Test
	public void testPrinting() {
		Printer p = new Printer();
		p.powerOn();
		p.print("Eureka!");
	}
}
