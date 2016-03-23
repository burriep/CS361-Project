package chronotimer;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Run {
	private ArrayList<RacerRun> racerData;
	private Queue<Racer> notStartedQueue;
	private ArrayList<Racer> startedQueue;

	public Run() {
		racerData = new ArrayList<RacerRun>();
		notStartedQueue = new LinkedList<Racer>();
		startedQueue = new ArrayList<Racer>();
	}

	public Queue<Racer> getRacers() {
		return notStartedQueue;
	}

	public void addRacer(Racer r) {
		notStartedQueue.add(r);
	}

	public void swapRacer() {
		// TODO
	}

	public void clearRacer(Racer r) {
		notStartedQueue.remove(r);
	}

	public void didNotFinishRacer() {
		// TODO
	}

	public void addRacerStartTime(String t) {
		if (!notStartedQueue.isEmpty()) {
			Racer r = notStartedQueue.remove();
			startedQueue.add(r);
			racerData.add(new RacerRun(r, t));
		}
	}

	public void addRacerEndTime(String t) {
		if (!startedQueue.isEmpty()) {
			Racer r = startedQueue.remove(0);
			RacerRun rr = findRacerRun(r);
			if (rr != null) {
				rr.setEndTime(t);
			}
		}
	}

	public void addRacerEndTime(Racer r, String t) {
		if (!startedQueue.isEmpty()) {
			startedQueue.remove(r);
			RacerRun rr = findRacerRun(r);
			if (rr != null) {
				rr.setEndTime(t);
			}
		}
	}

	private RacerRun findRacerRun(Racer r) {
		for (RacerRun rr : racerData) {
			if (rr.getRacer().equals(r)) {
				return rr;
			}
		}
		return null;
	}

	public Collection<RacerRun> getData() {
		return racerData;
	}

	public Collection<Racer> getStartedRacers() {
		return startedQueue;
	}

	public Collection<Racer> getQueuedRacers() {
		return notStartedQueue;
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(racerData);
	}
}
