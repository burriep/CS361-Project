package chronotimer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChronoTimer implements Observer {

	private boolean powerState;
	private Printer printer;
	private Timer timer;
	private ArrayList<Event> events;
	private Channel[] channels;
	private EventController ec;
	public static final int DEFAULT_CHANNEL_COUNT = 12;

	/**
	 * Create a new ChronoTimer to be used when timing races. This ChronoTimer
	 * will operate in real-time.
	 */
	public ChronoTimer() {
		this(false);
	}

	/**
	 * Create a new ChronoTimer to be used when timing races. If
	 * <code>testMode</code> is true, this ChronoTimer will not run its internal
	 * timer. Any changes to the time must be made through
	 * {@link #setTime(String) setTime()} and any calls to {@link #getTime()
	 * getTime()} will return the same time until the time is manually changed.
	 * <br>
	 * If <code>testMode</code> is false, this ChronoTimer will operate in
	 * real-time mode with the timer running.
	 * 
	 * @param testMode
	 *            - true = test mode on, false = test mode off.
	 */
	public ChronoTimer(boolean testMode) {
		powerOn();
		printer = new Printer();
		printer.powerOn();
		timer = new Timer(!testMode);
		events = new ArrayList<Event>();
		newEvent(EventType.IND);
		channels = new Channel[DEFAULT_CHANNEL_COUNT];
		for (int i = 0; i < channels.length; i++) {
			channels[i] = new Channel();
		}
		ec = new IndEventController();
		powerOff(); // must be explicitly turned on by client
	}

	public void powerOn() {
		powerState = true;
	}

	public void powerOff() {
		powerState = false;
	}

	public boolean isOn() {
		return powerState;
	}

	public boolean printerIsOn() {
		return printer.isOn();
	}

	/**
	 * Remove all data and reset the ChronoTimer to its initial state
	 */
	public void reset() {
		// ? Can we reset when the ChronoTimer is powered off?
		boolean bakPower = isOn();
		powerOn();
		timer = new Timer();
		printer.powerOff();
		events.clear();
		newEvent(EventType.IND);
		for (int i = 0; i < channels.length; i++) {
			if(channels[i].getSensor() != null) {
				channels[i].getSensor().deleteObservers();
			}
			channels[i] = new Channel();
		}
		if (!bakPower) {
			powerOff();
		}
	}

	/**
	 * Connect the appropriate sensor on the appropriate channel
	 * 
	 * @param s
	 * @param channelNumber
	 */
	public void connectSensor(Sensor s, int channelNumber) {
		if (isOn()) {
			if (channelNumber > 0 && channelNumber <= channels.length) {
				channels[channelNumber - 1].connect(s);
				s.addObserver(this);
			}
		}
	}

	/**
	 * Disconnects a sensor from channel <code>channelNumber</code>
	 * 
	 * @param channelNumber
	 */
	public void disconnectSensor(int channelNumber) {
		if (isOn()) {
			if (channelNumber > 0 && channelNumber <= channels.length) {
				Channel c = channels[channelNumber - 1];
				if (c.isConnected()) {
					c.getSensor().deleteObserver(this);
					c.disconnect();
				}
			}
		}
	}

	/**
	 * Toggle the appropriate channel using its index in the array
	 * 
	 * @param channelNumber
	 */
	public void toggleChannel(int channelNumber) {
		if (isOn()) {
			if (channelNumber > 0 && channelNumber <= channels.length) {
				Channel c = channels[channelNumber - 1];
				if (c.isConnected()) {
					c.toggleState();
				}
			}
		}
	}

	/**
	 * Add a new event to the ChronoTimer
	 * 
	 * @param e
	 */
	public void newEvent(EventType e) {
		if (isOn()) {
			if (e == null) {
				e = EventType.IND;
			}
			if (getCurrentEvent() == null) {
				events.add(new Event(e));
			} else if (e != getCurrentEvent().getType()) {
				getCurrentEvent().setType(e);
				switch (e) {
				case PARGRP:
					// TODO: Sprint 4
					// ec = new ParGrpEventController();
					break;
				case GRP:
					// TODO: Sprint 3
					// ec = new GrpEventController();
					break;
				case PARIND:
					ec = new ParIndEventController();
					break;
				default:
					ec = new IndEventController();
					break;
				}
			}
		}
	}

	/**
	 * Print run data for the current run.
	 */
	public void printCurrentRun() {
		if (isOn()) {
			Event cE = getCurrentEvent();
			Collection<RacerRun> data = cE.getCurrentRun().getData();
			printer.print("Run " + cE.getCurrentRunNumber());
			for (RacerRun rr : data) {
				printer.print(rr.toString());
			}
		}
	}

	/**
	 * Print run data for run #<code>id</code>.
	 * 
	 * @param id
	 *            must be a valid run number for the current event.
	 */
	public void printRun(int id) {
		if (isOn()) {
			Event cE = getCurrentEvent();
			if (id > 0 && id <= cE.getCurrentRunNumber()) {
				Collection<RacerRun> data = cE.getRun(id).getData();
				printer.print("Run " + cE.getCurrentRunNumber());
				for (RacerRun rr : data) {
					printer.print(rr.toString());
				}
			}
		}
	}

	public void exportCurrentRun() {
		exportRun(getCurrentEvent().getCurrentRunNumber());
	}

	public void exportRun(int id) {
		if (isOn()) {
			Event cE = getCurrentEvent();
			if (id > 0 && id <= cE.getCurrentRunNumber()) {
				String runJSON = cE.getRun(id).toJSON();
				try {
					FileWriter fw = new FileWriter("Run" + cE.getCurrentRunNumber() + ".json");
					fw.write(runJSON);
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Add a racer to the current run of the current event
	 */
	public void addRacerToCurrentRun(Racer r) {
		if (isOn()) {
			getCurrentEvent().getCurrentRun().addRacer(r);
		}
	}

	public void clearRacer(int racerID) {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			getCurrentEvent().getCurrentRun().clearRacer(new Racer(racerID));
		}
	}

	public void swapRacer() {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			getCurrentEvent().getCurrentRun().swapRacer();
		}
	}

	public void dnfRacer() {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			getCurrentEvent().getCurrentRun().didNotFinishRacer();
		}
	}

	/**
	 * Add a run to the current event. Must end the current run first.
	 */
	public void newRunCurrentEvent() {
		if (isOn()) {
			Event cE = getCurrentEvent();
			cE.newRun();
		}
	}

	/**
	 * End current run of the current event
	 */
	public void endRunCurrentEvent() {
		if (isOn()) {
			getCurrentEvent().endRun();
			ec.endRun();
		}
	}

	/**
	 * Get the list of events
	 * 
	 * @return events
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}

	/**
	 * Set the system time
	 * 
	 * @param t
	 *            - time
	 */
	public void setTime(String t) {
		// if (isOn()) {}
		// TODO: verify if ChronoTimer can be off for this command
		timer.setTime(t);
	}

	/**
	 * Get the system time
	 * 
	 * @return time
	 */
	public String getTime() {
		// if (isOn()) {}
		// TODO: verify if ChronoTimer can be off for this command
		return timer.getTime();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (isOn()) {
			if (o instanceof Sensor) {
				for (int i = 0; i < channels.length; ++i) {
					// find which channel the sensor is connected to
					if (o.equals(channels[i].getSensor())) {
						if (channels[i].isEnabled()) {
							ec.channelTriggered(i + 1, getCurrentEvent().getCurrentRun(), timer.getTime());
						}
						break;
					}
				}
			}
		}
	}

	private Event getCurrentEvent() {
		if (events.size() > 0) {
			return events.get(events.size() - 1);
		}
		return null;
	}
}
