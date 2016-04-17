package chronotimer;

import java.util.*;

public class ParIndEventController extends EventController {
	private Map<Integer, Integer> startChannelMap;
	private Map<Integer, Integer> finishChannelMap;
	private boolean firstLane;

	public ParIndEventController(Timer t, Run currentRun) {
		super(t, currentRun);
		startChannelMap = new HashMap<Integer, Integer>();
		finishChannelMap = new HashMap<Integer, Integer>();
		firstLane = true;
	}

	@Override
	public void channelTriggered(int channelNumber) {
		if (channelNumber == 1 || channelNumber == 3) {
			List<Integer> queue = run.getQueuedRacers();
			for (int qr : queue) {
				if (startChannelMap.getOrDefault(qr, -1) == channelNumber) {
					run.addRacerStartTime(qr, timer.getTime());
					startChannelMap.remove(qr);
					break;
				}
			}
		} else if (channelNumber == 2 || channelNumber == 4) {
			List<Integer> racers = run.getStartedRacers();
			for (Integer cr : racers) {
				if (finishChannelMap.getOrDefault(cr, -1) == channelNumber) {
					run.addRacerEndTime(cr, timer.getTime());
					finishChannelMap.remove(cr);
					break;
				}
			}
		}
	}

	@Override
	public void addRacer(int id) {
		run.addRacer(id);
		// save which channel this racer will start and end on
		int startChannel = firstLane ? 1 : 3;
		int finishChannel = firstLane ? 2 : 4;
		startChannelMap.put(id, startChannel);
		finishChannelMap.put(id, finishChannel);
		firstLane = !firstLane; // alternate between first lane and second lane
	}

	@Override
	public void endRun() {
		run.setActive(false);
		finishChannelMap.clear();
	}
}
