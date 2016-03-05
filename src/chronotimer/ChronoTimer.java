package chronotimer;

import java.util.*;

public class ChronoTimer {

	private boolean powerState; // true for on, false for off
	private Printer printer;
	private Timer timer;
	private ArrayList<Event> events;
	private Channel[] channels;
	private IndEventController indEventController;

	public ChronoTimer() {
		printer = new Printer();
		timer = new Timer();
		events = new ArrayList<Event>();
		channels = new Channel[8];
		for (int i = 0; i < channels.length; i++) {
			channels[i] = new Channel();
		}
		indEventController = new IndEventController();
		this.newEvent(new Event(EventType.IND));
	}

	public void powerOn() {
		powerState = true;
	}

	public void powerOff() {
		powerState = false;
	}

	public boolean getPowerState() {
		return powerState;
	}

	public boolean getPrinterState() {
		return printer.isOn();
	}

	/**
	 * Remove all data and reset the ChronoTimer to its initial state
	 */
	public void reset() {
		timer = new Timer();
		printer = new Printer();
		events.clear();
		this.newEvent(new Event(EventType.IND));
		for (int i = 0; i < channels.length; i++) {
			channels[i] = new Channel();
		}
		indEventController = new IndEventController();
	}

	/**
	 * Connect the appropriate sensor on the appropriate channel
	 * 
	 * @param connectSens
	 * @param channelNumber
	 */
	public void connectSensor(Sensor connectSens, int channelNumber) {
		channels[channelNumber - 1].connect(connectSens);
	}

	/**
	 * Toggle the appropriate channel using its index in the array
	 * 
	 * @param channelNumber
	 */
	public void toggleChannel(int channelNumber) {
		if (channels[channelNumber - 1].isConnected()) {
			channels[channelNumber - 1].toggleState();
		}
	}

	/**
	 * Trigger the appropriate channel using its index in the array
	 * 
	 * @param channelNumber
	 */
	public void triggerChannel(int channelNumber) {
		channels[channelNumber - 1].trigger(); // doesn't do anything
		if (channelNumber == 1){
			events.get(events.size()-1).getCurrentRun().addRacerStartTime(timer.getTime());
		} else if (channelNumber == 2) {
			events.get(events.size()-1).getCurrentRun().addRacerEndTime(timer.getTime());
		}
	}

	/**
	 * Add a new event to the ChronoTimer
	 * 
	 * @param addEvent
	 */
	public void newEvent(Event addEvent) {
		this.events.add(addEvent);
	}

	/**
	 * Print each event's run information
	 */
	public void printData() {
		Run cR = events.get(events.size() - 1).getCurrentRun();
		printer.print("Run " + events.get(events.size() - 1).getCurrentRunNumber());
		Collection<RacerRun> data = cR.getData();
		for (RacerRun rr : data) {
			printer.print(rr.toString());
		}
	}

	/**
	 * Add a racer to the current run of the current event
	 */
	public void addRacerToCurrentRun(Racer r) {
		events.get(events.size() - 1).getCurrentRun().addRacer(r);
	}

	/**
	 * Add a run to the current event
	 */
	public void addRunToCurrentEvent(Run newRun) {
		events.get(events.size() - 1).newRun();
	}

	/**
	 * End current run of the current event
	 */
	public void endCurrentEventCurrentRun() {
		events.get(events.size() - 1).endRun();
	}
	/**
	 * Set the system time
	 * @param t - time
	 */
	public void setTime(String t) {
		timer.setTime(t);
	}

	/**
	 * Get the system time
	 * @return time
	 */
	public String getTime() {
		return timer.getTime();
	}
}
