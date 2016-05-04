package chronotimer;

import java.util.*;

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
		if (startTime == null && channelNumber == 1) {
			startTime = timer.getTime();
			for (int i = 1; i <= lanes; i++) {
				tempID = finishChannelMap.getOrDefault(i, -1);
				if (tempID >= 0){
					run.addRacer(tempID, startTime);
				}
			}
		}
		else if (startTime != null){
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
		
		// the running time
		out.append(Timer.getDifference(startTime, timer.getTime()));
		out.append("\n");
		
		for (int i = runData.size() - 1; i >= 0; --i) {
			RacerRun rr = runData.get(i);
			if(rr.getEndTime() != null) {
				out.append(rr.getRacer()).append(" ");
				out.append(rr.getElapsedTime()).append(" F\n");
				break;
			}
		}
		return out.toString();
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

