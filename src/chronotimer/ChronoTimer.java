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
		for (int i = 0; i <= channels.length - 1; i++) {
			channels[i] = null;
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
		channels[channelNumber - 1].trigger();
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
		for (int i = 0; i < events.size(); i++) {
			printer.print("Event #" + Integer.toString(i + 1));
			ArrayList<Run> printRuns = events.get(i).getRuns();
			for (int j = 0; j < printRuns.size(); j++) {
				// STUB
			}
		}
	}

	/**
	 * Add a racer to the current run of the current event
	 */
	public void addRacerToCurrentRun(Racer r) {
		events.get(events.size() - 1).getCurrentRun().addRacer(r);
	}

}
