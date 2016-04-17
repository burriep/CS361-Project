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
	 * Set the RunType to be used.
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
				Run cr = runs.get(runs.size() - 1);
				switch (runType) {
				case PARGRP:
					ec = new ParGrpEventController(timer, cr);
					break;
				case GRP:
					ec = new GrpEventController(timer, cr);
					break;
				case PARIND:
					ec = new ParIndEventController(timer, cr);
					break;
				default:
					ec = new IndEventController(timer, cr);
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

	public void exportCurrentRun() {
		exportRun(runs.size());
	}

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
	 * Add a run to the current event. Must end the current run first.
	 */
	public void newRun() {
		if (isOn() && !runs.get(runs.size() - 1).isActive()) {
			Run cr = new Run();
			runs.add(cr);
			// assign new event controller for new Run
			switch (runType) {
			case PARGRP:
				ec = new ParGrpEventController(timer, cr);
				break;
			case GRP:
				ec = new GrpEventController(timer, cr);
				break;
			case PARIND:
				ec = new ParIndEventController(timer, cr);
				break;
			default:
				ec = new IndEventController(timer, cr);
				break;
			}
		}
	}

	/**
	 * End current run of the current event
	 */
	public void endRun() {
		if (isOn()) {
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
