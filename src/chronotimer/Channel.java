package chronotimer;

public class Channel {
	
	
	private boolean channelState;			//true for on, false for off
	private Sensor mySens;
	
	public void connect(Sensor s) {
		mySens = s;
	}

	public void disconnect() {
		mySens = null;
	}

	public void trigger() {
		
	}

	public void toggleState() {
		if(channelState == true) channelState = false;
		else channelState = true;
	}

	public boolean getState() {
		return channelState;
	}
}
