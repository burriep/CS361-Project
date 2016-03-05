package chronotimer;

import java.util.*;

public class Run {

	private Map<Racer, RacerRun> racerData;
	private Queue<Racer> notStartedQueue;
	private Queue<Racer> startedQueue;

	public Run() {
		racerData = new HashMap<Racer, RacerRun>();
		notStartedQueue = new LinkedList<Racer>();
		startedQueue = new LinkedList<Racer>();
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
			racerData.put(r, new RacerRun(r, t));
		}
	}

	public void addRacerEndTime(String t) {
		if (!startedQueue.isEmpty()) {
			Racer r = startedQueue.remove();
			racerData.get(r).setEndTime(t);
		}
	}
}
