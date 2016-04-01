package chronotimer;

import java.util.*;
import com.google.gson.Gson;

public class Run {
	private ArrayList<RacerRun> racerData;
	private Queue<Integer> notStartedQueue;
	private ArrayList<Integer> startedQueue;

	/**
	 * Create a new Run with no data.
	 */
	public Run() {
		racerData = new ArrayList<RacerRun>();
		notStartedQueue = new LinkedList<Integer>();
		startedQueue = new ArrayList<Integer>();
	}

	/**
	 * Add a racer to the queue of racers waiting to start. <code>r</code> must
	 * be within the range [0, 99999]. If it is outside the valid range, no
	 * racer will be added.
	 * 
	 * @param r
	 *            - the racer to add. Must be within the range [0, 99999].
	 */
	public void addRacer(int r) {
		if (r >= 0 && r <= 99999) {
			notStartedQueue.add(r);
		}
	}

	/**
	 * Exchanges the next two competitors to finish if more than one Racer has
	 * started but not finished.
	 */
	public void swapRacer() {
		if (startedQueue.size() >= 2) {
			int r0 = startedQueue.get(0);
			startedQueue.set(0, startedQueue.get(1));
			startedQueue.set(1, r0);
		}
	}

	/**
	 * Removes racer <code>r</code> from the queue of racers that have not yet
	 * started. <code>r</code> must be within the range [0, 99999]. If it is
	 * outside the valid range, no racer will be cleared.
	 * 
	 * @param r
	 *            - the racer to remove. Must be within the range [0, 99999].
	 */
	public void clearRacer(int r) {
		if (r >= 0 && r <= 99999) {
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
			int r = notStartedQueue.remove();
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
			int r = startedQueue.remove(0);
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
	 *            - the racer which to add the start time for. Must be within
	 *            the range [0, 99999].
	 * @param t
	 *            - the end time for racer r. Must be in the format HH:MM:SS.ss.
	 *            If the time is invalid, no end time will be recorded for that
	 *            racer but that racer will no longer be active in this Run.
	 */
	public void addRacerEndTime(int r, String t) {
		if (!startedQueue.isEmpty() && r >= 0 && r <= 99999) {
			startedQueue.remove(Integer.valueOf(r));
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

	private RacerRun findRacerRun(int r) {
		for (RacerRun rr : racerData) {
			if (rr.getRacer() == r) {
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
	public Collection<Integer> getQueuedRacers() {
		return notStartedQueue;
	}

	/**
	 * Returns a Collection of racers who have started but have not finished.
	 * Never NULL.
	 * 
	 * @return Collection of started racers.
	 */
	public Collection<Integer> getStartedRacers() {
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
