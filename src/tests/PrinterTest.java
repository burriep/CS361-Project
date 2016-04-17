package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chronotimer.*;

public class PrinterTest {
	@Test
	public void testInitialization() {
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
		p.print("This should not print.");
		p.powerOn();
		p.print("This should print.");
	}
}
