package chronotimer;

public class Channel {

	private boolean enabled;
	private Sensor button;
	private Sensor mySens;

	public void connect(Sensor s) {
		mySens = s;
	}

	public void connectButton(Sensor s) {
		button = s;
	}

	public void disconnect() {
		mySens = null;
	}

	public void disconnectButton() {
		button = null;
	}

	public Sensor getSensor() {
		return mySens;
	}

	public Sensor getButton() {
		return button;
	}

	/**
	 * Returns true if a sensor is connected and false if a sensor is not
	 * connected
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return mySens != null || button != null;
	}

	public void toggleState() {
		enabled = !enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
