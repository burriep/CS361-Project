package chronotimer;

import java.util.*;

public class ChronoTimer {
	
	private boolean powerState;  //true for on, false for off
	private Printer printer;
	private Timer timer;
	private ArrayList<Event> events;
	private Channel[] channels;
	private IndEventController indEventController;
	
	
	public ChronoTimer () {
		printer= new Printer();
		timer = new Timer();
		events = new ArrayList<Event>();
		this.newEvent(new Event(EventType.IND));
		channels = new Channel[8];
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
	
	public void connectSensor(Sensor connectSens, int channelNumber) {
		channels[channelNumber].connect(connectSens);
	}
	
	public void toggleChannel(int channelNumber) {
		channels[channelNumber].toggleState();
	}
	
	public void triggerChannel(int channelNumber) {
		channels[channelNumber].trigger();
	}
	
	public void newEvent(Event newEvent) {
		events.add(newEvent);
	}
	
}
