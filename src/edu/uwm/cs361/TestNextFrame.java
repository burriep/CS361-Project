package edu.uwm.cs361;
import static org.junit.Assert.*;

import org.junit.Test;
public class TestNextFrame {

		@Test
		public void testSpareCountsNextFrameScore(){
			ScoreSheet s1 = new ScoreSheet();
			s1.addThrow(7);
			s1.addThrow(3);
			s1.addThrow(5);
			assertEquals(s1.getFrameScore(1) , 15);
			assertEquals(s1.getGameScore(), 20);
		}
		
		@Test
		public void testStrikeMovesToNextFrame(){
			ScoreSheet s1 = new ScoreSheet();
			s1.getCurrentFrame();
			s1.addThrow(10);
			assertEquals(s1.getCurrentFrame(), 2);
		}
		
		@Test
		public void testStrikeCountsNextFrameScores(){
			ScoreSheet s1 = new ScoreSheet();
			s1.addThrow(10);
			s1.addThrow(6);
			s1.addThrow(3);
			assertEquals(s1.getFrameScore(1), 19);
			assertEquals(s1.getGameScore(), 28);
		}
		
}
