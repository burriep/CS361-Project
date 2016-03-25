package chronotimer;

import java.util.*;

public class Sensor extends Observable {
	private SensorType type;

	/**
	 * Create a new sensor of the given type.
	 * 
	 * @param type
	 *            - The type of sensor. If type == null, the type will be set to
	 *            SensorType.PUSH
	 */
	public Sensor(SensorType type) {
		if (type != null) {
			this.type = type;
		} else {
			this.type = SensorType.PUSH;
		}
	}

	/**
	 * Get they type for this sensor
	 * 
	 * @return the type of this Sensor
	 */
	public SensorType getSensorType() {
		return type;
	}

	/**
	 * Trigger this sensor. Any objects that this Sensor was connected to (that
	 * are observing this Sensor) will be notified.
	 */
	public void trigger() {
		setChanged();
		notifyObservers();
		clearChanged();
	}
}
