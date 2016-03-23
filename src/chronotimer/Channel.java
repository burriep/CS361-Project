package chronotimer;

public class Channel {

	private boolean enabled;
	private Sensor mySens;

	public void connect(Sensor s) {
		mySens = s;
	}

	public void disconnect() {
		mySens = null;
	}

	public Sensor getSensor() {
		return mySens;
	}

	/**
	 * Returns true if a sensor is connected and false if a sensor is not
	 * connected
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return mySens != null;
	}

	public void toggleState() {
		enabled = !enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
