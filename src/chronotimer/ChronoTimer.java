package chronotimer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChronoTimer implements Observer {

	private boolean powerState;
	private Printer printer;
	private Timer timer;
	private Channel[] channels;
	private ArrayList<Run> runs;
	private EventController ec;
	private RunType runType;
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
		runs = new ArrayList<Run>();
		runs.add(new Run());
		setEventType(RunType.IND);
		channels = new Channel[DEFAULT_CHANNEL_COUNT];
		for (int i = 0; i < channels.length; i++) {
			channels[i] = new Channel();
		}
		ec = new IndEventController(timer, runs.get(runs.size() - 1));
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
		timer.reset();
		printer.powerOn();
		runs.clear();
		runs.add(new Run());
		setEventType(RunType.IND);
		for (int i = 0; i < channels.length; i++) {
			if (channels[i].getSensor() != null) {
				channels[i].getSensor().deleteObservers();
			}
			channels[i] = new Channel();
		}
		ec = new IndEventController(timer, runs.get(runs.size() - 1));
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
	 * Connect a manual push button to this channel.
	 * 
	 * @param s
	 *            - the push button sensor
	 * @param channelNumber
	 *            - the channel number to connect the sensor to. Must be within
	 *            range [1, DEFAULT_CHANNEL_COUNT].
	 */
	public void connectButton(Sensor s, int channelNumber) {
		if (isOn()) {
			if (channelNumber > 0 && channelNumber <= channels.length) {
				channels[channelNumber - 1].connectButton(s);
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

	// trigger channel
	@Override
	public void update(Observable o, Object arg) {
		if (isOn()) {
			if (o instanceof Sensor) {
				for (int i = 0; i < channels.length; ++i) {
					// find which channel the sensor is connected to
					if (o.equals(channels[i].getSensor()) || o.equals(channels[i].getButton())) {
						if (channels[i].isEnabled()) {
							ec.channelTriggered(i + 1);
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * Add a new event to the ChronoTimer
	 * 
	 * @param e
	 */
	public void setEventType(RunType e) {
		if (isOn()) {
			if (e == null) {
				e = RunType.IND;
			}
			if (runType != e) {
				runType = e;
				switch (runType) {
				case PARGRP:
					ec = new ParGrpEventController(timer, runs.get(runs.size() - 1));
					break;
				case GRP:
					ec = new GrpEventController(timer, runs.get(runs.size() - 1));
					break;
				case PARIND:
					ec = new ParIndEventController(timer, runs.get(runs.size() - 1));
					break;
				default:
					ec = new IndEventController(timer, runs.get(runs.size() - 1));
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
			Collection<RacerRun> data = runs.get(runs.size() - 1).getData();
			printer.print("Run " + runs.size());
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
			if (id > 0 && id <= runs.size()) {
				Collection<RacerRun> data = runs.get(id - 1).getData();
				printer.print("Run " + runs.size());
				for (RacerRun rr : data) {
					printer.print(rr.toString());
				}
			}
		}
	}

	public void exportCurrentRun() {
		exportRun(runs.size());
	}

	public void exportRun(int id) {
		if (isOn()) {
			if (id > 0 && id <= runs.size()) {
				String runJSON = runs.get(id - 1).toJSON();
				try {
					FileWriter fw = new FileWriter("Run" + runs.size() + ".json");
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
	public void addRacer(int r) {
		if (isOn()) {
			ec.addRacer(r);
		}
	}

	public void clearRacer(int racerID) {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			runs.get(runs.size() - 1).clearRacer(racerID);
		}
	}

	public void swapRacer() {
		if (isOn() && runType == RunType.IND) {
			runs.get(runs.size() - 1).swapRacer();
		}
	}

	public void dnfRacer() {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			runs.get(runs.size() - 1).didNotFinishRacer();
		}
	}

	/**
	 * Add a run to the current event. Must end the current run first.
	 */
	public void newRun() {
		if (isOn() && !runs.get(runs.size() - 1).isActive()) {
			runs.add(new Run());
			// assign new event controller for new Run
			switch (runType) {
			case PARGRP:
				ec = new ParGrpEventController(timer, runs.get(runs.size() - 1));
				break;
			case GRP:
				ec = new GrpEventController(timer, runs.get(runs.size() - 1));
				break;
			case PARIND:
				ec = new ParIndEventController(timer, runs.get(runs.size() - 1));
				break;
			default:
				ec = new IndEventController(timer, runs.get(runs.size() - 1));
				break;
			}
		}
	}

	/**
	 * End current run of the current event
	 */
	public void endRun() {
		if (isOn()) {
			runs.get(runs.size() - 1).setActive(false);
			ec.endRun();
		}
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
		return timer.getTimeString();
	}
}
