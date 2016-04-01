package chronotimer;

/**
 * A RacerRun is the association of a Racer to a start and end time. Only one
 * racer can be in a single RacerRun, however a Racer can be in more than one
 * RacerRun. See the Race and Event classes for details on that.
 */
public class RacerRun {
	private int racerID;
	private Time startTime;
	private Time endTime;

	/**
	 * Create a new RacerRun for racer with ID racerID with no start or end
	 * time.
	 * 
	 * @param racerID
	 *            - the ID associated with this RacerRun
	 */
	public RacerRun(int racerID) {
		this.racerID = racerID;
	}

	/**
	 * Create a new RacerRun for racer with ID <code>racerID</code> and start
	 * time <code>start</code> and no end time.
	 * 
	 * @param racerID
	 *            - the ID associated with this RacerRun.
	 * @param start
	 *            - the start time for this RacerRun.
	 */
	public RacerRun(int racerID, Time start) {
		this.racerID = racerID;
		startTime = start;
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
	 * Set the start time for this RacerRun.
	 * 
	 * @param time
	 *            - start time. If NULL, no change is made to the start time.
	 */
	public void setStartTime(Time time) {
		if (time != null) {
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
	 * @param time
	 *            - end time. If NULL, no change is made to the start time.
	 */
	public void setEndTime(Time time) {
		if (time != null) {
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
		return racerID + " " + Timer.convertToTimeString(startTime) + " - " + Timer.convertToTimeString(endTime) + " : "
				+ getElapsedTime() + "s";
	}
}
