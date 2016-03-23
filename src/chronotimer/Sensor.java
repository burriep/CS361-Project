package chronotimer;

import java.util.*;

public class Sensor extends Observable {
	private SensorType type;

	public Sensor(SensorType type) {
		this.type = type;
	}

	public SensorType getSensorType() {
		return type;
	}

	public void trigger() {
		setChanged();
		notifyObservers();
		clearChanged();
	}
}
