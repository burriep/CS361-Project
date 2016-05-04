package chronotimer;

import java.util.*;

public class ParGrpEventController extends EventController {
	private Map<Integer, Integer> finishChannelMap;
	private int lanes = 1;
	private Time startTime;

	public ParGrpEventController(Timer t, Run currentRun) {
		super(t, currentRun);
		finishChannelMap = new HashMap<>();
	}

	@Override
	public void channelTriggered(int channelNumber) {
		int tempID;
		if (startTime == null && channelNumber == 1) {
			startTime = timer.getTime();
			for (int i = 1; i <= lanes; i++) {
				tempID = finishChannelMap.getOrDefault(i, -1);
				if (tempID >= 0) {
					run.addRacer(tempID, startTime);
				}
			}
		} else if (startTime != null) {
			tempID = finishChannelMap.getOrDefault(channelNumber, -1);
			if (tempID >= 0) {
				run.addRacerEndTime(tempID, timer.getTime());
				finishChannelMap.remove(channelNumber);
			}
		}
	}

	@Override
	public String getRunningDisplay() {
		StringBuilder out = new StringBuilder();
		List<RacerRun> runData = run.getData();

		if (startTime == null) {
			// run not started
			for (int i = 1; i <= 8; ++i) {
				int racerID = finishChannelMap.getOrDefault(i, -1);
				if (racerID >= 0)
					out.append(racerID).append("\n");
				else
					break;
			}
		} else {
			// run started
			double runningTime = Timer.getDifference(startTime, timer.getTime());
			for (int i = 0; i < runData.size(); ++i) {
				RacerRun rr = runData.get(i);
				if (rr.getEndTime() != null) {
					out.append(rr.getRacer()).append(" ");
					out.append(rr.getElapsedTime()).append(" F\n");
				} else {
					out.append(rr.getRacer()).append(" ");
					out.append(runningTime).append(" R\n");
				}
			}
		}

		return out.toString();
	}

	@Override
	public void addRacer(int id) {
		if (lanes <= 8 && startTime == null) {
			finishChannelMap.put(lanes, id);
			lanes++;
		}
	}

	public void clearRacer(int id) {
		if (finishChannelMap.containsValue(id)) {
			for (int i = 1; i <= lanes; i++) {
				if (finishChannelMap.getOrDefault(i, -1) == id) {
					finishChannelMap.remove(i);
					break;
				}
			}
		}
	}

	public void dnfRacer() {
		// not applicable to this race type
	}
}
