package chronotimer;

import java.util.*;

public class Event {

	private EventType type;
	private ArrayList<Run> runs;
	private boolean runIsActive;

	public Event(EventType t) {
		type = t;
		runs = new ArrayList<Run>();
		newRun();
	}

	public void newRun() {
		runs.add(new Run());
		runIsActive = true;
	}

	public void endRun() {
		runIsActive = false;
	}

	public void setType(EventType t) {
		type = t;
	}

	public EventType getType() {
		return type;
	}

	public Run getCurrentRun() {
		return runs.get(runs.size() - 1);
	}

	/**
	 * get the run specified by runNumber. If runNumber is out of bounds, NULL
	 * is returned.
	 * 
	 * @param runNumber
	 * @return the specified run or NULL.
	 */
	public Run getRun(int runNumber) {
		if (runNumber < runs.size()) {
			return runs.get(runNumber);
		} else {
			return null;
		}
	}

	public ArrayList<Run> getRuns() {
		return runs;
	}
}
