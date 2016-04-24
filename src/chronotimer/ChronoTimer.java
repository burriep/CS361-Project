package chronotimer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChronoTimer implements Observer {

	private boolean powerState;
	private Printer printer;
	private Timer timer;
	private Channel[] channels;
	private List<Run> runs;
	private EventController ec;
	private RunType runType;
	private WebClient web;
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
		web = new WebClient();
		powerOff(); // must be explicitly turned on by client
	}

	/**
	 * Powers this ChronoTimer ON so that it can be interacted with.
	 */
	public void powerOn() {
		powerState = true;
		// newRun();
	}

	/**
	 * Powers this ChronoTimer OFF so that no changes can be made to the data.
	 * Powering OFF the ChronoTimer ends the current run.
	 */
	public void powerOff() {
		// endRun();
		powerState = false;
	}

	/**
	 * Check if this ChronoTimer is ON.
	 * 
	 * @return true if power is ON.
	 */
	public boolean isOn() {
		return powerState;
	}

	/**
	 * Check if the printer power is ON.
	 * 
	 * @return true if printer power is ON.
	 */
	public boolean printerIsOn() {
		return printer.isOn();
	}

	/**
	 * Remove all data and reset the ChronoTimer to its initial state.
	 */
	public void reset() {
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
				channels[i].disconnect();
			}
		}
		if (!bakPower) {
			powerOff();
		}
	}

	/**
	 * Connect a sensor to the given channel. If Precondition is not met, no
	 * change will be made.<br>
	 * 
	 * @pre no sensor is currently connected to the channel number given.
	 * @pre Sensor s is not null.<br>
	 * @pre channel is a valid channel number from [1,
	 *      {@link #DEFAULT_CHANNEL_COUNT DEFAULT_CHANNEL_COUNT}]
	 * 
	 * @param s
	 *            - the sensor to connect.
	 * @param channel
	 *            - the channel number to connect the Sensor to.
	 */
	public void connectSensor(Sensor s, int channel) {
		if (isOn() && s != null && channel > 0 && channel <= channels.length) {
			Channel c = channels[channel - 1];
			if (c.getSensor() == null) {
				c.connect(s);
				s.addObserver(this);
			}
		}
	}

	/**
	 * Connect a manual push button to this channel. If Precondition is not met,
	 * no change will be made.<br>
	 * 
	 * @pre no sensor is currently connected to the channel number given.
	 * @pre Sensor s is not null.<br>
	 * @pre channel is a valid channel number from [1,
	 *      {@link #DEFAULT_CHANNEL_COUNT DEFAULT_CHANNEL_COUNT}]
	 * 
	 * @param s
	 *            - the push button sensor to connect
	 * @param channel
	 *            - the channel number to connect the sensor to.
	 */
	public void connectButton(Sensor s, int channel) {
		if (isOn() && s != null && channel > 0 && channel <= channels.length) {
			Channel c = channels[channel - 1];
			if (c.getButton() == null) {
				c.connectButton(s);
				s.addObserver(this);
			}
		}
	}

	/**
	 * Disconnects a sensor from channel <code>channelNumber</code>. If
	 * Precondition is not met, no change will be made.<br>
	 * 
	 * @pre A sensor is connected to the channel.
	 * @pre channel is a valid channel number from [1,
	 *      {@link #DEFAULT_CHANNEL_COUNT DEFAULT_CHANNEL_COUNT}]
	 * 
	 * @param channelNumber
	 *            - the channel number to disconnect the sensor.
	 */
	public void disconnectSensor(int channelNumber) {
		if (isOn() && channelNumber > 0 && channelNumber <= channels.length) {
			Channel c = channels[channelNumber - 1];
			if (c.getSensor() != null) {
				c.getSensor().deleteObserver(this);
				c.disconnect();
			}
		}
	}

	/**
	 * Toggle the given channel. If Precondition is not met, no change will be
	 * made.<br>
	 * 
	 * @pre channel is a valid channel number from [1,
	 *      {@link #DEFAULT_CHANNEL_COUNT DEFAULT_CHANNEL_COUNT}]
	 * @pre A sensor (or button) is connected to this channel.
	 * 
	 * @param channelNumber
	 *            - the channel number to toggle.
	 */
	public void toggleChannel(int channelNumber) {
		if (isOn() && channelNumber > 0 && channelNumber <= channels.length) {
			Channel c = channels[channelNumber - 1];
			if (c.isConnected()) {
				c.toggleState();
			}
		}
	}

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
	 * Set the RunType to be used.
	 * 
	 * @param e
	 *            - the RunType which will be used.
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
					ec = new ParGrpEventController(timer, getCurrentRun());
					break;
				case GRP:
					ec = new GrpEventController(timer, getCurrentRun());
					break;
				case PARIND:
					ec = new ParIndEventController(timer, getCurrentRun());
					break;
				default:
					ec = new IndEventController(timer, getCurrentRun());
					break;
				}
			}
		}
	}

	/**
	 * Print run data for the current run.
	 * 
	 * @return the text that was printed.
	 */
	public String printCurrentRun() {
		return printRun(runs.size());
	}

	/**
	 * Print run data for run #<code>id</code>.
	 * 
	 * @param id
	 *            must be a valid run number.
	 * @return the text that was printed.
	 */
	public String printRun(int id) {
		if (isOn()) {
			if (id > 0 && id <= runs.size()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Run ").append(id).append("\n");
				sb.append(runs.get(id - 1).toString());
				printer.print(sb.toString());
				sb.append("\n");
				return sb.toString();
			}
		}
		return "";
	}

	/**
	 * Exports the current run to a file named Run#.json where # is the current
	 * run number.
	 */
	public void exportCurrentRun() {
		exportRun(runs.size());
	}

	/**
	 * Exports the run with the given id to a file named Run&lt;id&gt;.json. If
	 * precondition is not met, no data will be exported.
	 * 
	 * @pre id must be a valid run number from 1 to current run.
	 * 
	 * @param id
	 *            - the run number to export.
	 */
	public void exportRun(int id) {
		if (isOn()) {
			if (id > 0 && id <= runs.size()) {
				String runJSON = runs.get(id - 1).toJSON();
				try {
					FileWriter fw = new FileWriter("Run" + id + ".json");
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
	public void addRacer(int racer) {
		if (isOn()) {
			ec.addRacer(racer);
		}
	}

	/**
	 * Get the list of runs for testing
	 * 
	 * @return List<Run>
	 */
	public List<Run> getRuns() {
		return runs;
	}

	private Run getCurrentRun() {
		return runs.get(runs.size() - 1);
	}

	public void clearRacer(int racer) {
		if (isOn()) {
			ec.clearRacer(racer);
		}
	}

	public void swapRacer() {
		if (isOn()) {
			ec.swapRacer();
		}
	}

	public void dnfRacer() {
		if (isOn()) {
			ec.dnfRacer();
		}
	}

	/**
	 * Start a new run of the same type. Must end the current run first.
	 */
	public void newRun() {
		if (isOn() && !getCurrentRun().isActive()) {
			runs.add(new Run());
			// assign new event controller for new Run
			switch (runType) {
			case PARGRP:
				ec = new ParGrpEventController(timer, getCurrentRun());
				break;
			case GRP:
				ec = new GrpEventController(timer, getCurrentRun());
				break;
			case PARIND:
				ec = new ParIndEventController(timer, getCurrentRun());
				break;
			default:
				ec = new IndEventController(timer, getCurrentRun());
				break;
			}
		}
	}

	/**
	 * End current run of the current event.
	 * 
	 * @pre Current run must be active.
	 */
	public void endRun() {
		if (isOn()) {
			Run cr = getCurrentRun();
			if (cr.isActive()) {
				ec.endRun();
				web.sendData(cr.toJSON());
			}
		}
	}

	/**
	 * Set the system time
	 * 
	 * @param t
	 *            - time
	 */
	public void setTime(String t) {
		timer.setTime(t);
	}

	/**
	 * Get the system time
	 * 
	 * @return time
	 */
	public String getTime() {
		return timer.getTimeString();
	}

	/**
	 * Get the Timer object
	 * 
	 * @return timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * Get the Printer object
	 * 
	 * @return printer
	 */
	public Printer getPrinter() {
		return printer;
	}

	/**
	 * Get information about the current run, specifically formatted for the
	 * running display on the ChronoTimer.
	 * 
	 * @return current run info
	 */
	public String getRunningDisplay() {
		if (isOn())
			return ec.getRunningDisplay();
		return "";
	}
}
