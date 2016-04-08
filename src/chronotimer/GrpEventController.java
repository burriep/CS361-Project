package chronotimer;

import java.util.*;

public class GrpEventController extends EventController {
	private Time startTime;
	private int place;
	private int lastPlaceAssigned;

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
			++place;
		}
	}

	@Override
	public void addRacer(int id) {
		List<RacerRun> list = run.getData();
		if (lastPlaceAssigned < list.size()) {
			list.get(lastPlaceAssigned).setRacer(id);
		}
		++lastPlaceAssigned;
	}

	@Override
	public void endRun() {
		run.setActive(false);
		startTime = null;
		place = 0;
	}
}
