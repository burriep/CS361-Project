package chronotimer;

abstract class EventController {
	protected Timer timer;
	protected Run run;

	public EventController(Timer t, Run currentRun) {
		timer = t;
		run = currentRun;
	}

	abstract public void channelTriggered(int channelNumber);

	public void addRacer(int id) {
		run.addRacer(id);
	}

	public void clearRacer(int id) {
		run.clearRacer(id);
	}

	public void swapRacer() {
		// Optional - depends on event type
	}

	public void dnfRacer() {
		run.dnfRacer();
	}

	public void endRun() {
		run.setActive(false);
	}

	abstract public String getRunningDisplay();
}
