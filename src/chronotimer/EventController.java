package chronotimer;

public interface EventController {
	public void channelTriggered(int channelNumber, Run r, String time);

	public void newRun(Event e);
}
