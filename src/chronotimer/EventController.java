package chronotimer;

public interface EventController {
	public void channelTriggered(int channelNumber, Run r, Time time);

	public void endRun();
}
