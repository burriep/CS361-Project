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

	abstract public void endRun();
}
