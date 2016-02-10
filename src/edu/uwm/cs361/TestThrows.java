package edu.uwm.cs361;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestThrows {
	
	
	@Test
	public void testOneThrow(){
		ScoreSheet s1 = new ScoreSheet();
		s1.addThrow(3);
		assertEquals(s1.getFrameScore(1), 3);
		assertEquals(s1.getGameScore(), 3);
	}
	
	@Test
	public void testTwoThrows(){
		ScoreSheet s1 = new ScoreSheet();
		s1.addThrow(3);
		s1.addThrow(6);
		assertEquals(s1.getFrameScore(1), 9);
		assertEquals(s1.getGameScore(), 9);
	}
	@Test
	public void testThreeThrows(){
		ScoreSheet s1 = new ScoreSheet();
		s1.addThrow(3);
		s1.addThrow(6);
		s1.addThrow(7);
		assertEquals(s1.getFrameScore(1), 9);
		assertEquals(s1.getFrameScore(2), 7);
		assertEquals(s1.getGameScore(), 16);
	}
}
