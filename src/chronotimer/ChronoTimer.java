package chronotimer;

import java.util.*;

public class ChronoTimer {
	
	private boolean powerState;  //true for on, false for off
	private ArrayList<Event> events = new ArrayList<Event>();
	private Printer printer= new Printer();
	private Timer timer = new Timer();
	private ArrayList<Channel> channels = new ArrayList<Channel>(8);
	private IndEventController indEventController = new IndEventController();
	
	public void powerOn() {
		powerState = true;
	}

	public void powerOff() {
		powerState = false;
	}
	
	public void reset() {
		
	}
}
