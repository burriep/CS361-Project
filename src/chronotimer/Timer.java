package chronotimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Timer stores and manages the current time and includes utilities for working
 * with time.
 */
public class Timer {
	private Date startTime; // stores the time when real-time mode was started
	private Date offset; // stores the client-facing start time
	private SimpleDateFormat format;
	private boolean running;
	public static final String TIME_FORMAT = "HH:mm:ss.SS";

	/**
	 * Create a new Timer set to the current time. Real-time mode is off:
	 * getTime() will return the same time until start() is called or the time
	 * is changed with setTime().
	 */
	public Timer() {
		startTime = new Date();
		offset = (Date) startTime.clone();
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
		startTime = new Date();
		format = new SimpleDateFormat(TIME_FORMAT);
		format.setLenient(false);
		if (t != null) {
			try {
				offset = format.parse(t);
			} catch (ParseException e) {
				offset = (Date) startTime.clone();
			}
		} else {
			offset = (Date) startTime.clone();
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
			startTime = new Date();
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
	 * Returns the current time from this Timer
	 * 
	 * @return current time formated as {@link #TIME_FORMAT TIME_FORMAT}
	 */
	public String getTime() {
		if (running) {
			return (format.format(getAdjustedTime())).substring(0, 11);
		} else {
			return (format.format(offset)).substring(0, 11);
		}
	}

	private Date getAdjustedTime() {
		return new Date(offset.getTime() + ((new Date()).getTime() - startTime.getTime()));
	}

	/**
	 * Set the current time for this Timer. If not a valid time, the time is not
	 * changed.
	 * 
	 * @param time
	 *            - the current time formated as {@link #TIME_FORMAT
	 *            TIME_FORMAT}
	 */
	public void setTime(String time) {
		if (time != null) {
			try {
				offset = format.parse(time);
				startTime = new Date();
			} catch (ParseException e) {
			}
		}
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
		if (start != null && end != null) {
			start = getFullLengthTime(start);
			end = getFullLengthTime(end);
			SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
			f.setLenient(false);
			try {
				Date t1 = f.parse(start);
				Date t2 = f.parse(end);
				double diff = (t2.getTime() - t1.getTime()) / 1000.0;
				return (diff > 0) ? diff : 0;
			} catch (ParseException e) {
				return 0;
			}
		}
		return 0;
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
		t = getFullLengthTime(t);
		SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
		f.setLenient(false);
		try {
			f.parse(t);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
