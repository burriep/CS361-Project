package chronotimer;

import java.util.*;

public class Run {
	private ArrayList<RacerRun> racerData;
	private Queue<Racer> notStartedQueue;
	private Queue<Racer> startedQueue;

	public Run() {
		racerData = new ArrayList<RacerRun>();
		notStartedQueue = new LinkedList<Racer>();
		startedQueue = new LinkedList<Racer>();
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

	public void didNotFinishRacer(Racer r) {
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
			Racer r = startedQueue.remove();
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
}
