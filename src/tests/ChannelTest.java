package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import chronotimer.*;

public class ChannelTest {
	@Test
	public void testInitialization() {
		Channel c = new Channel();
		assertEquals(false, c.isEnabled());
		assertEquals(false, c.isConnected());
		assertEquals(null, c.getSensor());
		assertEquals(null, c.getButton());
	}

	@Test
	public void testState() {
		Channel c = new Channel();
		c.toggleState();
		assertEquals(true, c.isEnabled());
		c.toggleState();
		assertEquals(false, c.isEnabled());
	}

	@Test
	public void testSensorConnection() {
		Sensor s1 = new Sensor(SensorType.EYE);
		Sensor s2 = new Sensor(SensorType.PUSH);
		Channel c = new Channel();

		c.connect(s1);
		assertEquals(true, c.isConnected());
		assertEquals(s1, c.getSensor());
		c.disconnect();
		assertEquals(false, c.isConnected());

		c.connectButton(s2);
		assertEquals(true, c.isConnected());
		assertEquals(s2, c.getButton());
		c.disconnectButton();
		assertEquals(false, c.isConnected());

		c.connect(s1);
		c.connectButton(s2);
		assertEquals(true, c.isConnected());
		assertEquals(s1, c.getSensor());
		assertEquals(s2, c.getButton());
		c.disconnect();
		c.disconnectButton();
		assertEquals(false, c.isConnected());
	}
}
