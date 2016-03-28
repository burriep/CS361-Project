package chronotimer;

import java.util.*;

public class Event {

	private EventType type;
	private ArrayList<Run> runs;
	private boolean runIsActive;

	public Event(EventType t) {
		type = t;
		runs = new ArrayList<Run>();
		runIsActive = false;
		newRun();
	}

	public void newRun() {
		if (!isActive()) {
			runs.add(new Run());
			runIsActive = true;
		}
	}

	public void endRun() {
		runIsActive = false;
		getCurrentRun().clearQueue();
	}

	public boolean isActive() {
		return runIsActive;
	}

	public void setType(EventType t) {
		type = t;
	}

	public EventType getType() {
		return type;
	}

	public int getCurrentRunNumber() {
		return runs.size();
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
		if (runNumber > 0 && runNumber <= runs.size()) {
			return runs.get(runNumber - 1);
		} else {
			return null;
		}
	}

	public ArrayList<Run> getRuns() {
		return runs;
	}
}
