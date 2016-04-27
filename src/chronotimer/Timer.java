package chronotimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Timer stores and manages the current time and includes utilities for working
 * with time.
 */
public class Timer {
	private Time startTime; // stores the time when real-time mode was started
	private Time offset; // stores the client-facing start time
	private SimpleDateFormat format;
	private boolean running;
	public static final String TIME_FORMAT = "HH:mm:ss.SS";

	/**
	 * Create a new Timer set to the current time. Real-time mode is off:
	 * getTime() will return the same time until start() is called or the time
	 * is changed with setTime().
	 */
	public Timer() {
		startTime = new Time();
		offset = (Time) startTime.clone();
		format = new SimpleDateFormat(TIME_FORMAT);
		format.setLenient(false);
	}

	/**
	 * Create a new Timer set to the current time. If running == false,
	 * real-time mode is off: getTime() will return the same time until start()
	 * is called or the time is changed with setTime(). If running == true,
	 * real-time mode is on: getTime() will return a time >= the time at which
	 * this Timer was allocated or was set with setTime().
	 * 
	 * @param running
	 *            - set whether this timer is running in real-time mode or in
	 *            stopped mode.
	 */
	public Timer(boolean running) {
		this();
		this.running = running;
	}

	/**
	 * Creates a new Timer with the current time set to <code>t</code> if
	 * <code>t</code> is a valid time string. If <code>t</code> is not valid,
	 * the current time is used.
	 * 
	 * @param t
	 *            - the time to initialize this timer to.
	 */
	public Timer(String t) {
		startTime = new Time();
		format = new SimpleDateFormat(TIME_FORMAT);
		format.setLenient(false);
		if (t != null) {
			try {
				offset = new Time(format.parse(getFullLengthTime(t)).getTime());
			} catch (ParseException e) {
				offset = (Time) startTime.clone();
			}
		} else {
			offset = (Time) startTime.clone();
		}
	}

	/**
	 * Creates a new Timer with the current time set to <code>t</code> if
	 * <code>t</code> is a valid time string. If <code>t</code> is not valid,
	 * the current time is used.<br>
	 * If running == false, real-time mode is off: getTime() will return the
	 * same time until start() is called or the time is changed with setTime().
	 * If running == true, real-time mode is on: getTime() will return a time >=
	 * the time at which this Timer was allocated or was set with setTime().
	 * 
	 * @param t
	 *            - the time to initialize this timer to.
	 * @param running
	 *            - set whether this timer is running in real-time mode or in
	 *            stopped mode.
	 */
	public Timer(String t, boolean running) {
		this(t);
		this.running = running;
	}

	/**
	 * Starts this timer running in real-time mode if it was not running.
	 */
	public void start() {
		if (!running) {
			startTime = new Time();
			running = true;
		}
	}

	/**
	 * Stops this timer at the current time and takes it out of real-time mode
	 * if it was running.
	 */
	public void stop() {
		if (running) {
			offset = getAdjustedTime();
			running = false;
		}
	}

	/**
	 * Returns the current time from this Timer as a String.
	 * 
	 * @return current time formated as {@link #TIME_FORMAT TIME_FORMAT}
	 */
	public String getTimeString() {
		return format.format(getTime()).substring(0, 11);
	}

	/**
	 * Returns the current time from this Timer as a Time.
	 * 
	 * @return current time
	 */
	public Time getTime() {
		return running ? getAdjustedTime() : offset;
	}

	private Time getAdjustedTime() {
		return new Time(offset.getTime() + ((new Time()).getTime() - startTime.getTime()));
	}

	/**
	 * Set the current time for this Timer. If not a valid time, the time is not
	 * changed.
	 * 
	 * @param time
	 *            - the current time formated as {@link #TIME_FORMAT
	 *            TIME_FORMAT}
	 * @return true if the time was successfully set, false otherwise
	 */
	public boolean setTime(String time) {
		if (time == null) {
			return false;
		}
		try {
			offset = new Time(format.parse(getFullLengthTime(time)).getTime());
			startTime = new Time();
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Resets this Timer
	 */
	public void reset() {
		startTime = new Time();
		offset = (Time) startTime.clone();
	}

	/**
	 * Get the difference between 2 time strings.
	 * 
	 * @param start
	 *            - the start time. Must not be null. Must be formated as
	 *            {@link #TIME_FORMAT TIME_FORMAT}
	 * @param end
	 *            - the end time. Must not be null. Must be formated as
	 *            {@link #TIME_FORMAT TIME_FORMAT}
	 * @return (end - start) in seconds, or 0 if there is an error.
	 */
	public static double getDifference(String start, String end) {
		if (start == null || end == null) {
			return 0;
		}
		SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
		f.setLenient(false);
		try {
			return getDifference(new Time(f.parse(getFullLengthTime(start)).getTime()),
					new Time(f.parse(getFullLengthTime(end)).getTime()));
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * Get the difference between 2 Time objects in seconds.
	 * 
	 * @param start
	 *            - the start time. Must not be null.
	 * @param end
	 *            - the end time. Must not be null.
	 * @return (end - start) in seconds, or 0 if there is an error.
	 */
	public static double getDifference(Time start, Time end) {
		if (start == null || end == null) {
			return 0;
		}
		double diff = (end.getTime() - start.getTime()) / 1000.0;
		return (diff > 0) ? diff : 0;
	}

	/**
	 * Add trailing zeros to the seconds to turn tenths of a second or hundredth
	 * of a second into milliseconds
	 * 
	 * @param t
	 * @return
	 */
	private static String getFullLengthTime(String t) {
		int width = 12;
		if (t != null && t.length() < width) {
			t = t + new String(new char[width - t.length()]).replace('\0', '0');
		}
		return t;
	}

	/**
	 * Validates a string to determine if it is formated as {@link #TIME_FORMAT
	 * TIME_FORMAT} and is a valid time between 00:00:00.00 - 23:59:59.99
	 * 
	 * @param t
	 *            the String to validate
	 * @return true if <code>t</code> is a valid time string, false otherwise.
	 */
	public static boolean validateTime(String t) {
		if (t == null) {
			return false;
		}
		SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
		f.setLenient(false);
		try {
			f.parse(getFullLengthTime(t));
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Convert a Time to a time string in the format {@link #TIME_FORMAT
	 * TIME_FORMAT}.
	 * 
	 * @param time
	 *            - A Time object. If NULL, an empty string will be returned.
	 * @return A string representing <code>time</code> as a time.
	 */
	public static String convertToTimeString(Time time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
		return f.format(time).substring(0, 11);
	}

	public static Time convertToTime(String t) {
		if (t == null) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
		f.setLenient(false);
		try {
			return new Time(f.parse(getFullLengthTime(t)).getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Convert a duration (in seconds) to a time string in the format
	 * HH:MM:SS.thousandths.
	 * 
	 * @param duration
	 *            - time in seconds.
	 * @return duration in time form as a string.
	 */
	public static String durationToTimeString(double duration) {
		if (duration <= 0)
			return "DNF";
		if (duration < 60)
			return Double.toString(duration);
		int minutes = (int) (duration / 60);
		double seconds = duration - minutes;
		if (minutes < 60)
			return minutes + ":" + seconds;
		int hours = minutes / 60;
		return hours + ":" + minutes + ":" + seconds;
	}
}
