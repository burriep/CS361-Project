package chronotimer;

import java.util.Map;

public class ParGrpEventController extends EventController {
	private Map<Integer, Integer> finishChannelMap;
	private int lanes = 1; 
	private Time startTime;

	public ParGrpEventController(Timer t, Run currentRun) {
		super(t, currentRun);
	}

	@Override
	public void channelTriggered(int channelNumber) {
		int tempID;
		if (startTime == null) {
			startTime = timer.getTime();
			for (int i = 1; i <= lanes; i++) {
				tempID = finishChannelMap.getOrDefault(i, -1);
				if (tempID >= 0){
					run.addRacer(tempID, startTime);
				}
			}
		}
		else {
			tempID = finishChannelMap.getOrDefault(channelNumber, -1);
			if (tempID >= 0) {
				run.addRacerEndTime(tempID, timer.getTime());
				finishChannelMap.remove(channelNumber);
			}
		}
	}

	@Override
	public String getRunningDisplay() {
		return ""; // TODO: Sprint 4
	}
	
	@Override
	public void addRacer(int id) {
		finishChannelMap.put(lanes, id);
		lanes++;
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
	
	public void dnfRacer(){
		//TODO
	}
}

