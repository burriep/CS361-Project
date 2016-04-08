package chronotimer;

public class IndEventController extends EventController {
	public IndEventController(Timer t, Run currentRun) {
		super(t, currentRun);
	}

	@Override
	public void channelTriggered(int channelNumber) {
		if (channelNumber == 1) {
			run.addRacerStartTime(timer.getTime());
		} else if (channelNumber == 2) {
			run.addRacerEndTime(timer.getTime());
		}
	}

	@Override
	public void swapRacer() {
		run.swapRacer();
	}
}
