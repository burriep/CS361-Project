package chronotimer;

import java.util.*;

public class ChronoTimer implements Observer {

	private boolean powerState;
	private Printer printer;
	private Timer timer;
	private ArrayList<Event> events;
	private Channel[] channels;
	private EventController ec;
	public static final int DEFAULT_CHANNEL_COUNT = 12;

	public ChronoTimer() {
		powerOn();
		printer = new Printer();
		printer.powerOn();
		timer = new Timer();
		events = new ArrayList<Event>();
		newEvent(new Event(EventType.IND));
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
		newEvent(new Event(EventType.IND));
		for (int i = 0; i < channels.length; i++) {
			channels[i].getSensor().deleteObservers();
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
	public void newEvent(Event e) {
		if (isOn()) {
			if (e != null) {
				events.add(e);
				if (e.getType() == EventType.IND) {
					ec = new IndEventController();
				} else if (e.getType() == EventType.PARIND) {
					ec = new ParIndEventController();
				}
			}
		}
	}

	/**
	 * Print run data for the current run.
	 */
	public void printCurrentRun() {
		if (isOn()) {
			Event cE = events.get(events.size() - 1);
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
			Event cE = events.get(events.size() - 1);
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
		if (isOn()) {
			Event cE = events.get(events.size() - 1);
			String runJSON = cE.getCurrentRun().toJSON();
			// TODO
		}
	}

	public void exportRun(int id) {
		{
			Event cE = events.get(events.size() - 1);
			if (id > 0 && id <= cE.getCurrentRunNumber()) {
				String runJSON = cE.getRun(id).toJSON();
				// TODO
			}
		}
	}

	/**
	 * Add a racer to the current run of the current event
	 */
	public void addRacerToCurrentRun(Racer r) {
		if (isOn()) {
			events.get(events.size() - 1).getCurrentRun().addRacer(r);
		}
	}

	public void clearRacer(int racerID) {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			events.get(events.size() - 1).getCurrentRun().clearRacer(new Racer(racerID));
		}
	}

	public void swapRacer() {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			events.get(events.size() - 1).getCurrentRun().swapRacer();
		}
	}

	public void dnfRacer() {
		if (isOn()) {
			// TODO: check if this is right for PARIND and other race types
			events.get(events.size() - 1).getCurrentRun().didNotFinishRacer();
		}
	}

	/**
	 * Add a run to the current event. Must end the current run first.
	 */
	public void newRunCurrentEvent() {
		if (isOn()) {
			events.get(events.size() - 1).newRun();
		}
	}

	/**
	 * End current run of the current event
	 */
	public void endRunCurrentEvent() {
		if (isOn()) {
			events.get(events.size() - 1).endRun();
		}
	}

	/**
	 * Set the system time
	 * 
	 * @param t
	 *            - time
	 */
	public void setTime(String t) {
		if (isOn()) {
			timer.setTime(t);
		}
	}

	/**
	 * Get the system time
	 * 
	 * @return time
	 */
	public String getTime() {
		if (isOn()) {
			return timer.getTime();
		}
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (isOn()) {
			if (o instanceof Sensor) {
				for (int i = 0; i < channels.length; ++i) {
					// find which channel the sensor is connected to
					if (o.equals(channels[i].getSensor())) {
						if (channels[i].isEnabled()) {
							ec.channelTriggered(i + 1, events.get(events.size() - 1).getCurrentRun(), timer.getTime());
						}
						break;
					}
				}
			}
		}
	}
}
