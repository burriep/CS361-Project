package chronotimer;

public class ParGrpEventController extends EventController {
	public ParGrpEventController(Timer t, Run currentRun) {
		super(t, currentRun);
	}

	@Override
	public void channelTriggered(int channelNumber) {
	}

	@Override
	public String getRunningDisplay() {
		return ""; // TODO: Sprint 4
	}
}
