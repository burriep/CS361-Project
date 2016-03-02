package chronotimer;

public class Timer {
	String currentTime;

	/**
	 * Returns the current time from this Timer
	 * 
	 * @return current time as a string in the format HH:MM:SS.ss
	 */
	public String getTime() {
		return currentTime;
	}

	/**
	 * Set the current time for this Timer
	 * 
	 * @param time
	 *            - the current time. Must be in the format HH:MM:SS.ss
	 */
	public void setTime(String time) {
		if (validateTime(time)) {
			currentTime = time;
		}
	}

	/**
	 * Get the difference between 2 times.
	 * 
	 * @param start
	 *            - the start time. Must be in the format HH:MM:SS.ss
	 * @param end
	 *            - the end time. Must be in the format HH:MM:SS.ss
	 * @return (end - start) in seconds, as a double.
	 */
	public static double getDifference(String start, String end) {
		if (validateTime(start) && validateTime(end)) {
			// TODO
		}
		return 0.0;
	}

	/**
	 * Validates a string to determine if it follows the time format of
	 * HH:MM:SS.ss
	 */
	public static boolean validateTime(String t) {
		// TODO
		return false;
	}
}
