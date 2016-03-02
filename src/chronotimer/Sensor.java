package chronotimer;

public class Sensor {
	private SensorType type;

	public Sensor(SensorType type) {
		this.type = type;
	}

	public SensorType getSensorType() {
		return type;
	}
}
