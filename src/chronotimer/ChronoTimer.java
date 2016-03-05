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
		newEvent(new Event(EventType.IND));
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
		timer = new Timer();
		printer = new Printer();
		events.clear();
		this.newEvent(new Event(EventType.IND));
		for (int i = 0; i <= channels.length - 1; i++) {
			channels[i] = null;
		}
		indEventController = new IndEventController();
	}
	
	public void connectSensor(Sensor connectSens, int channelNumber) {
		channels[channelNumber-1].connect(connectSens);
	}
	
	public void toggleChannel(int channelNumber) {
		channels[channelNumber-1].toggleState();
	}
	
	public void triggerChannel(int channelNumber) {
		channels[channelNumber-1].trigger();
	}
	
	public void newEvent(Event newEvent) {
		events.add(newEvent);
	}
	
	public void printData() {
		for (int i = 0; i < events.size(); i++) {
			printer.print("Event #" + Integer.toString(i));
			ArrayList<Run> printRuns = events.get(i).getRuns();
			for (int j = 0; j < printRuns.size(); j++) {
				
			}
		}
	}
	
}
