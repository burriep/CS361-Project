package edu.uwm.cs361;

public class ScoreSheet {
	private Frame[] frames;
	private int gameScore;
	private int currentFrameIndex;
	private int currentThrow;

	private static final int FRAME_COUNT = 10;

	/**
	 * Create a new ScoreSheet.
	 */
	public ScoreSheet() {
		gameScore = 0;
		currentFrameIndex = 0;
		currentThrow = 1;
		frames = new Frame[FRAME_COUNT];
	}

	/**
	 * Add the score for another throw. <br>
	 * If this is the first throw in a frame, the score must be less than or
	 * equal to 10. <br>
	 * If this is the second throw in a frame, the sum of the first throw and
	 * this score must be less than or equal to 10. <br>
	 * Each frame has a maximum of 2 throws. If all 10 pins are knocked down on
	 * the 1st throw, the player has thrown a strike. <br>
	 * If all remaining pins are knocked down on the second throw, the player
	 * has thrown a spare. <br>
	 * <br>
	 * An IllegalStateException is thrown if this method is called after the
	 * 10th frame has been completed. An IllegalArgumentException is thrown if
	 * the value of pinsDown would cause the frame score to exceed 10.
	 * 
	 * @param pinsDown
	 *            - The number of pins knocked down on this throw.
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public void addThrow(int pinsDown) {
		// TODO
	}

	/**
	 * Returns the number of the current frame.
	 * 
	 * @return the number of the current frame.
	 */
	public int getCurrentFrame() {
		return currentFrameIndex + 1;
	}

	/**
	 * Returns the current throw number (1 or 2).
	 * 
	 * @return the current throw number (1 or 2).
	 */
	public int getCurrentThrow() {
		return currentThrow;
	}

	/**
	 * Returns the score for the given frame.
	 * 
	 * @param frame
	 *            - the frame number to get a score for. Must be greater than 0
	 *            and less than or equal to the current frame number.
	 * @return the score for the given frame.
	 */
	public int getFrameScore(int frame) {
		return 0; // TODO
	}

	/**
	 * Returns the current game score.
	 * 
	 * @return the current game score.
	 */
	public int getGameScore() {
		return gameScore;
	}
}
