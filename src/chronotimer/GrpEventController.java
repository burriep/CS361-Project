package chronotimer;

public class GrpEventController extends EventController {
	private Time startTime;
	private int place;

	public GrpEventController(Timer t, Run currentRun) {
		super(t, currentRun);
	}

	@Override
	public void channelTriggered(int channelNumber) {
		if (channelNumber == 1 && startTime == null) {
			// start channel
			startTime = timer.getTime();
			place = 1;
		} else if (channelNumber == 2 && startTime != null) {
			// end channel
			run.addRacer(place, startTime, timer.getTime());
		}
	}

	@Override
	public void addRacer(int id) {
		// TODO
	}

	@Override
	public void endRun() {
		startTime = null;
		place = 0;
	}
}
