package chronotimer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Timer stores and manages the current time and includes utilities for working
 * with times.
 *
 */
public class Timer {
	private String currentTime;
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SS");

	/**
	 * Creates a new Timer with the set to the current time.
	 */
	public Timer() {
		currentTime = (timeFormat.format(new Date())).substring(0, 11);
	}

	/**
	 * Creates a new Timer with the current time set to <code>t</code> if
	 * <code>t</code> is a valid time string. If <code>t</code> is not valid,
	 * the current time is used.
	 * 
	 * @param t
	 *            the time to initialize this timer to.
	 */
	public Timer(String t) {
		if (validateTime(t)) {
			currentTime = t;
		} else {
			currentTime = (timeFormat.format(new Date())).substring(0, 11);
		}
	}

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
	 * Get the difference between 2 time strings.
	 * 
	 * @param start
	 *            - the start time. Must not be null. Must be in the format
	 *            HH:MM:SS.ss
	 * @param end
	 *            - the end time. Must not be null. Must be in the format
	 *            HH:MM:SS.ss
	 * @return (end - start) in seconds, or 0 if there is an error.
	 */
	public static double getDifference(String start, String end) {
		if (validateTime(start) && validateTime(end)) {
			String[] startS = start.split(":");
			String[] endS = end.split(":");
			// hours + minutes + seconds
			double startT = Integer.parseInt(startS[0]) * 360 + Integer.parseInt(startS[1]) * 60
					+ Double.parseDouble(startS[2]);
			double endT = Integer.parseInt(endS[0]) * 360 + Integer.parseInt(endS[1]) * 60
					+ Double.parseDouble(endS[2]);
			double result = endT - startT;
			if (result >= 0) {
				return result;
			} else {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Validates a string to determine if it follows the time format of
	 * HH:MM:SS.ss and is a valid time between 00:00:00.00 - 23:59:59.99
	 * 
	 * @param t
	 *            the String to validate
	 * @return true if <code>t</code> is a valid time string, false otherwise.
	 */
	public static boolean validateTime(String t) {
		if (t == null) {
			return false;
		}
		if (t.length() < 10 || t.length() > 11) {
			return false;
		}
		String[] parts = t.split(":");
		if (parts.length != 3) {
			return false;
		}
		try {
			int h = Integer.parseInt(parts[0]);
			if (h < 0 || h >= 24) {
				return false;
			}
			int m = Integer.parseInt(parts[1]);
			if (m < 0 || m >= 60) {
				return false;
			}
			double s = Double.parseDouble(parts[2]);
			if (s < 0 || s >= 60) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
