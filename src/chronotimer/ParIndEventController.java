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
				channelMap.put(queue.iterator().next(), channelNumber);
			}

		} else if (channelNumber == 2 || channelNumber == 4) {
			Collection<Racer> racers = r.getStartedRacers();
			for (Racer cr : racers) {
				if (channelMap.get(cr) == channelNumber) {
					r.addRacerEndTime(cr, time);
					channelMap.remove(cr);
					break;
				}
			}
		}
	}

	@Override
	public void newEvent() {
		channelMap.clear();
	}
}
