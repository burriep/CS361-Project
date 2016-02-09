package edu.uwm.cs361;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestLastFrame {
	
	/** >>Are there just two throws in the last frame
	 * 	or are there three like in real bowling?
	 * Tests currently are set up for just two throws
	 * >>Would be easier if there were some set methods to go with many of 
	 * the get methods*/
	@Test
	public void testSpareOnLastFrame(){
		ScoreSheet s1 = new ScoreSheet();
		for(int i = 0; i<9; i++ ){
			s1.addThrow(8);
			s1.addThrow(1);
		}
		s1.addThrow(9);
		s1.addThrow(1);
		assertEquals(s1.getFrameScore(10), 10);
		assertEquals(s1.getGameScore(),91 );
	}
	@Test
	public void testStrikeOnLastFrame(){
		ScoreSheet s1 = new ScoreSheet();
		for(int i = 0; i<7; i++ ){
			s1.addThrow(8);
			s1.addThrow(1);
		}
		s1.addThrow(10);
		s1.addThrow(10);
		s1.addThrow(10);
		assertEquals(s1.getFrameScore(10), 10);
		assertEquals(s1.getGameScore(), 123);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testThrowOn11thFrame(){
		ScoreSheet s1 = new ScoreSheet();
		for(int i = 0; i<9; i++ ){
			s1.addThrow(8);
			s1.addThrow(1);
		}
		s1.addThrow(3);
		s1.addThrow(4);
		s1.addThrow(8);
	}
}
