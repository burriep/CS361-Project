package chronotimer;

/**
 * A RacerRun is the association of a Racer to a start and end time. Only one
 * racer can be in a single RacerRun, however a Racer can be in more than one
 * RacerRun. See the Race and Event classes for details on that.
 */
public class RacerRun {
	private Racer racer;
	private String startTime;
	private String endTime;

	/**
	 * Create a new RacerRun for Racer r with no start or end time.
	 * 
	 * @param r
	 *            - the racer. Should not be NULL.
	 */
	public RacerRun(Racer r) {
		racer = r;
	}

	/**
	 * Create a new RacerRun for Racer r with start time <code>start</code> and
	 * no end time.
	 * 
	 * @param r
	 *            - the racer. Should not be NULL.
	 * @param start
	 *            - the start time for this Racer's run. Must be in the format
	 *            HH:MM:SS.ss
	 */
	public RacerRun(Racer r, String start) {
		racer = r;
		if (Timer.validateTime(start)) {
			startTime = start;
		}
	}

	/**
	 * Return the racer associated with this RacerRun.
	 * 
	 * @return the racer associated with this RacerRun. Could be NULL if the
	 *         racer was null when creating this RacerRun.
	 */
	public Racer getRacer() {
		return racer;
	}

	/**
	 * Set the start time for this RacerRun. If <code>time</code> is not a valid
	 * time, the start time will not be changed.
	 * 
	 * @param time
	 *            - the start time. Must be in the format HH:MM:SS.ss
	 */
	public void setStartTime(String time) {
		if (Timer.validateTime(time)) {
			startTime = time;
		}
	}

	/**
	 * Return the start time associated with this RacerRun or NULL if the start
	 * time has not been set.
	 * 
	 * @return the start time (in the format HH:MM:SS.ss) or NULL if the start
	 *         time has not been added.
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Set the end time for this RacerRun. If <code>time</code> is not a valid
	 * time, the end time will not be changed.
	 * 
	 * @param time
	 *            - the end time. Must be in the format HH:MM:SS.ss
	 */
	public void setEndTime(String time) {
		if (Timer.validateTime(time)) {
			endTime = time;
		}
	}

	/**
	 * Return the end time associated with this RacerRun or NULL if the start
	 * time has not been set.
	 * 
	 * @return the end time (in the format HH:MM:SS.ss) or NULL if the end time
	 *         has not been added.
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * Get the elapsed time (end - start) for this RacerRun in seconds. If
	 * either time is not set, this will return 0.
	 * 
	 * @return the elapsed time for this RacerRun or 0 if either start or end
	 *         time is not set.
	 */
	public double getElapsedTime() {
		return Timer.getDifference(startTime, endTime);
	}

	@Override
	public String toString() {
		return racer + " " + startTime + " - " + endTime + " : " + Timer.getDifference(startTime, endTime) + "s";
	}
}
