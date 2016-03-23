package chronotimer;

public class IndEventController implements EventController {
	@Override
	public void channelTriggered(int channelNumber, Run r, String time) {
		if (channelNumber == 1) {
			r.addRacerStartTime(time);
		} else if (channelNumber == 2) {
			r.addRacerEndTime(time);
		}
	}
}
