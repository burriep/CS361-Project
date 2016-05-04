package chronotimer;

/**
 * A RacerRun is the association of a Racer to a start and end time. Only one
 * racer can be in a single RacerRun, however a Racer can be in more than one
 * RacerRun. See the Race and Event classes for details on that.<br>
 * 
 * The Racer ID must be within the range [0, 99999]<br>
 * The start Time must be before or equal to the end time.
 */
public class RacerRun {
	private int racerID;
	private Time startTime;
	private Time endTime;

	private static boolean isValidRacerID(int racerID) {
		return racerID >= 0 && racerID <= 99999;
	}

	private static boolean isValidTimeRange(Time start, Time end) {
		if (start == null || end == null)
			return true;
		return start.compareTo(end) <= 0;
	}

	/**
	 * Create a new RacerRun for racer with ID racerID with no start or end
	 * time.
	 * 
	 * @pre racerID must be in range [0, 99999]
	 * @param racerID
	 *            - the ID associated with this RacerRun
	 * @throws IllegalArgumentException
	 *             if precondition is not met
	 */
	public RacerRun(int racerID) throws IllegalArgumentException {
		this(racerID, null, null);
	}

	/**
	 * Create a new RacerRun for racer with ID <code>racerID</code> and start
	 * time <code>start</code> and no end time.
	 * 
	 * @pre racerID must be in range [0, 99999]
	 * @param racerID
	 *            - the ID associated with this RacerRun.
	 * @param start
	 *            - the start time for this RacerRun.
	 * @throws IllegalArgumentException
	 *             if precondition is not met
	 */
	public RacerRun(int racerID, Time start) throws IllegalArgumentException {
		this(racerID, start, null);
	}

	/**
	 * Create a new RacerRun for racer with ID <code>racerID</code> and start
	 * time <code>start</code> and end time <code>end</code>.
	 * 
	 * @pre racerID must be in range [0, 99999].
	 * @pre if both start and end are not null, start must be equal to or come
	 *      after end
	 * @param racerID
	 *            - the ID associated with this RacerRun.
	 * @param start
	 *            - the start time for this RacerRun.
	 * @param end
	 *            - the end time for this RacerRun
	 * @throws IllegalArgumentException
	 *             if precondition is not met
	 */
	public RacerRun(int racerID, Time start, Time end) throws IllegalArgumentException {
		if (!isValidRacerID(racerID))
			throw new IllegalArgumentException("Invalid ID");
		if (!isValidTimeRange(start, end))
			throw new IllegalArgumentException("End time is before start time");
		this.racerID = racerID;
		startTime = start;
		endTime = end;
	}

	/**
	 * Return the racer ID associated with this RacerRun.
	 * 
	 * @return the racer ID associated with this RacerRun.
	 */
	public int getRacer() {
		return racerID;
	}

	/**
	 * Set the racer for this RacerRun.
	 * 
	 * @pre racerID must be in range [0, 99999].<br>
	 *      If precondition is not met, no change will be made to the racer ID
	 *      associated with this RacerRun.
	 * 
	 * @param racerID
	 *            - the racer to associate with this RacerRun.
	 * 
	 */
	public void setRacer(int racerID) {
		if (isValidRacerID(racerID))
			this.racerID = racerID;
	}

	/**
	 * Set the start time for this RacerRun.
	 * 
	 * @pre If end time is not NULL, <code>time</code> must be before or equal
	 *      to the end time, or NULL.<br>
	 *      If precondition is not met, no change will be made.
	 * 
	 * @param time
	 *            - start time.
	 */
	public void setStartTime(Time time) {
		if (isValidTimeRange(time, endTime)) {
			startTime = time;
		}
	}

	/**
	 * Return the start time associated with this RacerRun or NULL if the start
	 * time has not been set.
	 * 
	 * @return the start time
	 */
	public Time getStartTime() {
		return startTime;
	}

	/**
	 * Set the end time for this RacerRun.
	 * 
	 * @pre If start time is not NULL, <code>time</code> must be equal or after
	 *      the start time, or NULL.<br>
	 *      If precondition is not met, no change will be made.
	 * 
	 * @param time
	 *            - end time.
	 */
	public void setEndTime(Time time) {
		if (isValidTimeRange(startTime, time)) {
			endTime = time;
		}
	}

	/**
	 * Return the end time associated with this RacerRun or NULL if the end time
	 * has not been set.
	 * 
	 * @return the end time
	 */
	public Time getEndTime() {
		return endTime;
	}

	/**
	 * Get the elapsed time (end - start) for this RacerRun in seconds. If
	 * either time is not set, this will return 0.
	 * 
	 * @return the elapsed time for this RacerRun.
	 */
	public double getElapsedTime() {
		return Timer.getDifference(startTime, endTime);
	}

	@Override
	public String toString() {
		return racerID + " " + startTime + " - " + endTime + " : " + getElapsedTime() + "s";
	}
}
