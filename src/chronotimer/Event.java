package chronotimer;
import java.util.*;

public class Event {
	
	private EventType type;
	private ArrayList<Run> runs;
	
	public Event(EventType t) {
		type = t;
		runs = new ArrayList<Run>();
	}
	
	public void newRun() {
		runs.add(new Run());
	}

	public void endRun() {
		runs.remove(0);
	}
	public void setType(EventType t){
		type = t;
	}
	
	public ArrayList<Run> getRuns() {
		return runs;
	}
}
