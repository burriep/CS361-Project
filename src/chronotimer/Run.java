package chronotimer;

import java.util.*;
import com.google.gson.Gson;

public class Run {
	private List<RacerRun> racerData;
	private List<Integer> notStartedQueue;
	private List<Integer> startedQueue;
	private boolean active;

	/**
	 * Create a new Run with no data.
	 */
	public Run() {
		racerData = new ArrayList<RacerRun>();
		notStartedQueue = new LinkedList<Integer>();
		startedQueue = new LinkedList<Integer>();
		active = true;
	}

	/**
	 * Return if this race is active.
	 * 
	 * @return if this race is active.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets if this Run is active or not
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Add a racer to the queue of racers waiting to start.<br>
	 * 
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * <code>racerID</code> must be within the range [0, 99999].<br>
	 * <code>racerID</code> must have not yet be added to this Run.<br>
	 * No racer will be added if either condition is not met.<br>
	 * 
	 * @param racerID
	 *            - the racer to add.
	 */
	public void addRacer(int racerID) {
		if (isActive() && isValidRacerID(racerID) && !containsRacer(racerID)) {
			notStartedQueue.add(racerID);
		}
	}

	/**
	 * Add a racer with a start time to this run. The racer will be added to the
	 * end of the list of racers currently running.<br>
	 * 
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * <code>racerID</code> must be within the range [0, 99999].<br>
	 * <code>racerID</code> must have not yet be added to this Run.<br>
	 * No racer will be added if either condition is not met.
	 * 
	 * @param racerID
	 *            - the racer to add.
	 * @param start
	 *            - the start time for this racer.
	 */
	public void addRacer(int racerID, Time start) {
		if (isActive() && isValidRacerID(racerID) && !containsRacer(racerID)) {
			startedQueue.add(racerID);
			racerData.add(new RacerRun(racerID, start));
		}
	}

	/**
	 * Add a racer with a start time and end time to this run. The racer will be
	 * added to the end of the list of racer who have started.<br>
	 * 
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * <code>racerID</code> must be within the range [0, 99999].<br>
	 * <code>racerID</code> must have not yet be added to this Run.<br>
	 * No racer will be added if either condition is not met.
	 * 
	 * @param racerID
	 *            - the racer to add.
	 * @param start
	 *            - the start time for this racer.
	 * @param end
	 *            - the end time for this racer.
	 */
	public void addRacer(int racerID, Time start, Time end) {
		if (isActive() && isValidRacerID(racerID) && !containsRacer(racerID)) {
			racerData.add(new RacerRun(racerID, start, end));
		}
	}

	private boolean isValidRacerID(int racerID) {
		return racerID >= 0 && racerID <= 99999;
	}

	private boolean containsRacer(int racerID) {
		boolean contained = notStartedQueue.contains(racerID);
		if (!contained) {
			for (RacerRun rr : racerData) {
				if (rr.getRacer() == racerID) {
					return true;
				}
			}
		}
		return contained;
	}

	/**
	 * Exchanges the next two racers to finish.<br>
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * There are at least 2 racers who have started, have not yet finished, and
	 * have not been marked as DNF. No racers will be swapped if the
	 * precondition is not met.
	 */
	public void swapRacer() {
		if (isActive() && startedQueue.size() >= 2) {
			int r0 = startedQueue.get(0);
			startedQueue.set(0, startedQueue.get(1));
			startedQueue.set(1, r0);

			r0 = racerData.get(0).getRacer();
			Time t0 = racerData.get(0).getStartTime();
			racerData.get(0).setRacer(racerData.get(1).getRacer());
			racerData.get(0).setStartTime(racerData.get(1).getStartTime());
			racerData.get(1).setRacer(r0);
			racerData.get(1).setStartTime(t0);
		}
	}

	/**
	 * Removes racer <code>racerID</code> from the queue of racers that have not
	 * yet started.<br>
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * <code>racerID</code> must be within the range [0, 99999].<br>
	 * No racer will be cleared if the precondition is not met.
	 * 
	 * @param racerID
	 *            - the racer to remove. Must be within the range [0, 99999].
	 */
	public void clearRacer(int racerID) {
		if (isValidRacerID(racerID)) {
			notStartedQueue.remove(racerID);
		}
	}

	/**
	 * The next racer to finish will not finish and is no longer running. The
	 * racer is removed from the list of racers currently running, but the racer
	 * and the start time of that racer are saved.
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * This Run contains a racer who has started but not yet finished.<br>
	 * No racer will be marked as DNF if the precondition is not met.
	 */
	public void dnfRacer() {
		if (isActive() && !startedQueue.isEmpty()) {
			startedQueue.remove(0);
		}
	}

	/**
	 * Add time <code>t</code> as the start time for the next racer in the queue
	 * to start and remove that racer from the queue to start.
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * The queue of racers to start is not empty.<br>
	 * <code>t</code> is not NULL.<br>
	 * No start time will be added and no changes will be made if precondition
	 * is not met.
	 * 
	 * @param t
	 *            - the start time for the next racer.
	 */
	public void addRacerStartTime(Time t) {
		if (!notStartedQueue.isEmpty()) {
			addRacerStartTime(notStartedQueue.get(0), t);
		}
	}

	/**
	 * Add time <code>t</code> as the start time for the next racer in the queue
	 * to start and remove that racer from the queue to start.
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * The queue of racers to start is not empty.<br>
	 * <code>racerID</code> must be within the range [0, 99999].<br>
	 * <code>racerID</code> has been queued to start and has not yet started<br>
	 * <code>t</code> is not NULL.<br>
	 * No start time will be added and no changes will be made if precondition
	 * is not met.
	 * 
	 * @param racerID
	 *            - the racer to associate with the start time.
	 * @param t
	 *            - the start time for racer <code>racerID</code>.
	 */
	public void addRacerStartTime(int racerID, Time t) {
		if (isActive() && isValidRacerID(racerID) && t != null && !notStartedQueue.isEmpty()) {
			boolean wasQueued = notStartedQueue.remove(Integer.valueOf(racerID));
			if (!wasQueued) {
				return; // racer was not in notStartedQueue
			}
			startedQueue.add(racerID);
			racerData.add(new RacerRun(racerID, t));
		}
	}

	/**
	 * Add time <code>t</code> as the end time for the next racer to finish and
	 * remove that racer from the list of started racers.
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * The list of racers who have started (but not finished and were not marked
	 * as DNF) is not empty.<br>
	 * <code>t</code> is not NULL.<br>
	 * No end time will be added and no changes will be made if precondition is
	 * not met.
	 * 
	 * @param t
	 *            - the end time for the next racer.
	 */
	public void addRacerEndTime(Time t) {
		if (!startedQueue.isEmpty()) {
			addRacerEndTime(startedQueue.get(0), t);
		}
	}

	/**
	 * Add time <code>t</code> as the end time for racer <code>racerID</code>
	 * and remove that racer from the list of started racers.
	 * <strong>Precondition:</strong><br>
	 * This Run is active and has not been ended.<br>
	 * <code>racerID</code> must be within the range [0, 99999].<br>
	 * <code>racerID</code> has started but has not finished and has not been
	 * marked as DNF<br>
	 * <code>t</code> is not NULL.<br>
	 * No end time will be added and no changes will be made if precondition is
	 * not met.
	 * 
	 * @param racerID
	 *            - the racer to associate with the end time.
	 * @param t
	 *            - the end time for racer <code>racerID</code>.
	 */
	public void addRacerEndTime(int racerID, Time t) {
		if (isActive() && isValidRacerID(racerID) && t != null && !startedQueue.isEmpty()) {
			boolean hadStarted = startedQueue.remove(Integer.valueOf(racerID));
			if (!hadStarted) {
				return; // racer was not in startedQueue
			}
			RacerRun rr = findRacerRun(racerID);
			if (rr != null) {
				rr.setEndTime(t);
			}
		}
	}

	private RacerRun findRacerRun(int r) {
		for (RacerRun rr : racerData) {
			if (rr.getRacer() == r) {
				return rr;
			}
		}
		return null;
	}

	/**
	 * Removes all racers from the queue.
	 */
	public void clearQueue() {
		notStartedQueue.clear();
		startedQueue.clear();
	}

	/**
	 * Returns this Run's racer data in JSON format. All Racers that have
	 * started are included in this data.
	 * 
	 * @return JSON string representation of the racer data.
	 */
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(racerData);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Iterator<RacerRun> it = racerData.iterator(); it.hasNext();) {
			RacerRun rr = it.next();
			sb.append(rr.toString());
			if (it.hasNext()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Returns a List of racers that are queued to start but have not yet
	 * started. Never NULL.
	 * 
	 * @return the queue of racers waiting to start.
	 */
	public List<Integer> getQueuedRacers() {
		return notStartedQueue;
	}

	/**
	 * Returns a List of racers who have started but have not finished. Never
	 * NULL.
	 * 
	 * @return the list of racers who have started and are currently active.
	 */
	public List<Integer> getStartedRacers() {
		return startedQueue;
	}

	/**
	 * List of RacerRuns containing the data associating a Racer to a start and
	 * end time. Never NULL.
	 * 
	 * @return the list of RacerRun data in this Run.
	 */
	public List<RacerRun> getData() {
		return racerData;
	}
}
