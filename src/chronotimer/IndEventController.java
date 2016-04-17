package chronotimer;

import java.util.List;

public class IndEventController extends EventController {
	public IndEventController(Timer t, Run currentRun) {
		super(t, currentRun);
	}

	@Override
	public void channelTriggered(int channelNumber) {
		if (channelNumber == 1) {
			run.addRacerStartTime(timer.getTime());
		} else if (channelNumber == 2) {
			run.addRacerEndTime(timer.getTime());
		}
	}

	@Override
	public void swapRacer() {
		run.swapRacer();
	}

	@Override
	public String getRunningDisplay() {
		StringBuilder out = new StringBuilder();
		List<Integer> queued = run.getQueuedRacers();
		List<Integer> started = run.getStartedRacers();
		List<RacerRun> runData = run.getData();

		// the next three to start
		for (int i = 0; i < queued.size() && i < 3; i++) {
			out.append(queued.get(i));
			if (i == 2)
				out.append(" >");
			out.append("\n");
		}
		out.append("\n");

		// the current running racers
		for (int sr : started) {
			out.append(sr).append(" ");
			// current running time
			for (int i = runData.size() - 1; i >= 0; --i) {
				RacerRun rr = runData.get(i);
				if (rr.getRacer() == sr) {
					out.append(Timer.getDifference(rr.getStartTime(), timer.getTime()));
					break;
				}
			}
			out.append(" R \n");
		}
		out.append("\n");

		// the last racer to finish.
		for (int i = runData.size() - 1; i >= 0; --i) {
			RacerRun rr = runData.get(i);
			if (rr.getEndTime() != null) {
				out.append(rr.getRacer()).append(" ");
				out.append(rr.getElapsedTime()).append(" F\n");
				break;
			}
		}
		return out.toString();
	}
}
