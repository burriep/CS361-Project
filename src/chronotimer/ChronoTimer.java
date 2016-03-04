package chronotimer;

import java.util.*;

public class ChronoTimer {
	
	private boolean powerState;  //true for on, false for off
	private Printer printer;
	private Timer timer;
	private ArrayList<Event> events;
	private ArrayList<Channel> channels;
	private IndEventController indEventController;
	
	
	public ChronoTimer () {
		printer= new Printer();
		timer = new Timer();
		events = new ArrayList<Event>();
		channels = new ArrayList<Channel>(8);
		indEventController = new IndEventController();
	}
	
	public void powerOn() {
		powerState = true;
	}

	public void powerOff() {
		powerState = false;
	}
	
	public void reset() {
		
	}
}
