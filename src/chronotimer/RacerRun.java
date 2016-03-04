package chronotimer;

/**
 * A RacerRun is the association of a Racer to a start and end time. Only one
 * racer can be in a single RacerRun, however a Racer can be in more than one
 * RacerRun. See the Race and Event classes for details on that.
 *
 */
public class RacerRun {
	private String startTime;
	private String endTime;
	private Racer racer;

	/**
	 * Create a new RacerRun for Racer r with no start or end time.
	 * 
	 * @param r
	 */
	public RacerRun(Racer r) {
		racer = r;
	}

	/**
	 * Return the racer associated with this RacerRun.
	 * 
	 * @return the racer associated with this RacerRun
	 */
	public Racer getRacer() {
		return racer;
	}

	/**
	 * Set the start time for this RacerRun
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
	 * Set the end time for this RacerRun
	 * 
	 * @param time
	 *            - the end time. Must be in the format HH:MM:SS.ss
	 */
	public void setEndTime(String time) {
		if (Timer.validateTime(time)) {
			endTime = time;
		}
	}

	public String getEndTime() {
		return endTime;
	}

	public double getElapsedTime() {
		return Timer.getDifference(startTime, endTime);
	}
}
