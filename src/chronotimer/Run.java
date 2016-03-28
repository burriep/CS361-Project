package chronotimer;

import java.util.*;
import com.google.gson.Gson;

public class Run {
	private ArrayList<RacerRun> racerData;
	private Queue<Racer> notStartedQueue;
	private ArrayList<Racer> startedQueue;

	/**
	 * Create a new Run with no data.
	 */
	public Run() {
		racerData = new ArrayList<RacerRun>();
		notStartedQueue = new LinkedList<Racer>();
		startedQueue = new ArrayList<Racer>();
	}

	/**
	 * Add a racer to the queue of racers waiting to start. <code>r</code> must
	 * not be NULL. If <code>r</code> is NULL, nothing will be added or changed.
	 * 
	 * @param r
	 *            - the racer to add, must not be NULL.
	 */
	public void addRacer(Racer r) {
		if (r != null) {
			notStartedQueue.add(r);
		}
	}

	/**
	 * Exchanges the next two competitors to finish if more than one Racer has
	 * started but not finished.
	 */
	public void swapRacer() {
		if (startedQueue.size() >= 2) {
			Racer r0 = startedQueue.get(0);
			startedQueue.set(0, startedQueue.get(1));
			startedQueue.set(1, r0);
		}
	}

	/**
	 * Removes Racer <code>r</code> from the queue of racers that have not yet
	 * started. If <code>r</code> is NULL, no racers will be removed or changed.
	 * 
	 * @param r
	 *            - the Racer to remove. Should not be NULL.
	 */
	public void clearRacer(Racer r) {
		if (r != null) {
			notStartedQueue.remove(r);
		}
	}

	/**
	 * The next Racer to finish will not finish and is marked as such.
	 */
	public void didNotFinishRacer() {
		// TODO
	}

	/**
	 * Add time <code>t</code> as the start time for the next racer in the queue
	 * to start.
	 * 
	 * @param t
	 *            - the start time for the next racer. Must be in the format
	 *            HH:MM:SS.ss. If the time is invalid, no start time will be
	 *            recorded for that racer but will be marked as started.
	 */
	public void addRacerStartTime(String t) {
		if (!notStartedQueue.isEmpty()) {
			Racer r = notStartedQueue.remove();
			startedQueue.add(r);
			racerData.add(new RacerRun(r, t));
		}
	}

	/**
	 * Add an end time for the next racer to finish.
	 * 
	 * @param t
	 *            - the end time for the next racer. Must be in the format
	 *            HH:MM:SS.ss. If the time is invalid, no end time will be
	 *            recorded for that racer but that racer will no longer be
	 *            active in this Run.
	 */
	public void addRacerEndTime(String t) {
		if (!startedQueue.isEmpty()) {
			Racer r = startedQueue.remove(0);
			RacerRun rr = findRacerRun(r);
			if (rr != null) {
				rr.setEndTime(t);
			}
		}
	}

	/**
	 * Add an end time for a specific racer which has started.
	 * 
	 * @param r
	 *            - the racer which to add the start time for. If NULL, no
	 *            changes will be made.
	 * @param t
	 *            - the end time for Racer r. Must be in the format HH:MM:SS.ss.
	 *            If the time is invalid, no end time will be recorded for that
	 *            racer but that racer will no longer be active in this Run.
	 */
	public void addRacerEndTime(Racer r, String t) {
		if (!startedQueue.isEmpty() && r != null) {
			startedQueue.remove(r);
			RacerRun rr = findRacerRun(r);
			if (rr != null) {
				rr.setEndTime(t);
			}
		}
	}

	public void clearQueue() {
		notStartedQueue.clear();
		startedQueue.clear();
	}

	private RacerRun findRacerRun(Racer r) {
		for (RacerRun rr : racerData) {
			if (rr.getRacer().equals(r)) {
				return rr;
			}
		}
		return null;
	}

	/**
	 * Returns a Collection of racers that are queued to start but have not yet
	 * started. Never NULL.
	 * 
	 * @return Collection of not started racers.
	 */
	public Collection<Racer> getQueuedRacers() {
		return notStartedQueue;
	}

	/**
	 * Returns a Collection of racers who have started but have not finished.
	 * Never NULL.
	 * 
	 * @return Collection of started racers.
	 */
	public Collection<Racer> getStartedRacers() {
		return startedQueue;
	}

	/**
	 * Collection of RacerRuns containing the data associating a Racer to a
	 * start and end time. Never NULL.
	 * 
	 * @return
	 */
	public Collection<RacerRun> getData() {
		return racerData;
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
}
