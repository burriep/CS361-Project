package chronotimer;

import java.util.*;

public class ParIndEventController extends EventController {
	Map<Integer, Integer> channelMap;

	public ParIndEventController(Timer t, Run currentRun) {
		super(t, currentRun);
		channelMap = new HashMap<Integer, Integer>();
	}

	@Override
	public void channelTriggered(int channelNumber) {
		if (channelNumber == 1 || channelNumber == 3) {
			Collection<Integer> queue = run.getQueuedRacers();
			if (!queue.isEmpty()) {
				int finishChannel = (channelNumber == 1) ? 2 : 4;
				Iterator<Integer> qi = queue.iterator();
				if (qi.hasNext()) {
					channelMap.put(qi.next(), finishChannel);
				}
				run.addRacerStartTime(timer.getTime());
			}
		} else if (channelNumber == 2 || channelNumber == 4) {
			Collection<Integer> racers = run.getStartedRacers();
			for (Integer cr : racers) {
				if (channelMap.getOrDefault(cr, -1) == channelNumber) {
					run.addRacerEndTime(cr, timer.getTime());
					channelMap.remove(cr);
					break;
				}
			}
		}
	}

	@Override
	public void endRun() {
		channelMap.clear();
	}
}
