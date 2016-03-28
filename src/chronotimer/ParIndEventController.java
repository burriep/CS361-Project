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
				r.addRacerStartTime(time);
				int finishChannel = (channelNumber == 1) ? 2 : 4;
				Iterator<Racer> qi = queue.iterator();
				if (qi.hasNext()) {
					channelMap.put(qi.next(), finishChannel);
				}
			}
		} else if (channelNumber == 2 || channelNumber == 4) {
			Collection<Racer> racers = r.getStartedRacers();
			for (Racer cr : racers) {
				int finishChannel = channelMap.get(cr);
				if (finishChannel == channelNumber) {
					r.addRacerEndTime(cr, time);
					channelMap.remove(cr);
					break;
				}
			}
		}
	}

	@Override
	public void newRun(Event e) {
		if (!e.isActive()) {
			channelMap.clear();
		}
	}
}
