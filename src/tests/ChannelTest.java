package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import chronotimer.*;

public class ChannelTest {

	@Test
	public void testOneChannel() {
		Channel s = new Channel();
		assertEquals(false, s.getState());
		s.toggleState();
		assertEquals(true, s.getState());
		s.toggleState();
		assertEquals(false, s.getState());
	}

	@Test
	public void testMultipleChannels() {
		ArrayList<Channel> channels = new ArrayList<Channel>(12);
		for (int i = 0; i < channels.size(); i++) {
			assertEquals(false, channels.get(i).getState());
		}

		for (int i = 0; i < channels.size(); i++) {
			if (i % 2 == 0) {
				channels.get(i).toggleState();
			}
		}
		for (int i = 0; i < channels.size(); i++) {
			if (i % 2 == 0) {
				assertEquals(true, channels.get(i).getState());
			} else {
				assertEquals(false, channels.get(i).getState());
			}
		}
	}

	@Test
	public void testZeroChannels() {

	}

}
