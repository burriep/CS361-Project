package chronotimer;

import java.util.*;

public class ParIndEventController implements EventController {
	Map<Racer, Integer> channelMap;

	public ParIndEventController() {
		channelMap = new HashMap<Racer, Integer>();
	}

	@Override
	public void channelTriggered(int channelNumber, Run r, String time) {
		if (channelNumber == 1 || channelNumber == 3) {
			Collection<Racer> queue = r.getQueuedRacers();
			if (!queue.isEmpty()) {
				int finishChannel = (channelNumber == 1) ? 2 : 4;
				Iterator<Racer> qi = queue.iterator();
				if (qi.hasNext()) {
					channelMap.put(qi.next(), finishChannel);
				}
				r.addRacerStartTime(time);
			}
		} else if (channelNumber == 2 || channelNumber == 4) {
			Collection<Racer> racers = r.getStartedRacers();
			for (Racer cr : racers) {
				if (channelMap.getOrDefault(cr, -1) == channelNumber) {
					r.addRacerEndTime(cr, time);
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
