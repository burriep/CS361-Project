package edu.uwm.cs361;

public class Frame {
	private int score;
	private boolean strike;

	/**
	 * Create a new Frame object with an initial score of 0.
	 */
	public Frame() {
		score = 0;
	}

	/**
	 * Create a new Frame object with the provided initial score.
	 * 
	 * @param s
	 *            - Initial frame score
	 */
	public Frame(int s) {
		score = s;
	}

	/**
	 * Add to the score for this frame.
	 * 
	 * @param s
	 *            - The amount to add to the frame's score
	 */
	public void addScore(int s) {
		score += s;
	}

	/**
	 * Returns the current score for this frame.
	 * 
	 * @return The current score for this frame
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Mark the frame as being a strike. Only takes effect if the score for this
	 * frame is >= 10
	 * 
	 * @param s
	 *            - boolean specifying if this frame is a strike
	 */
	public void setStrike() {
		strike = true;
	}

	/**
	 * Check if this frame is a strike.
	 * 
	 * @return if this frame is a strike.
	 */
	public boolean isStrike() {
		return strike;
	}
}
