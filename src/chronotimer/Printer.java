package chronotimer;

public class Printer {
	private boolean powerState; // true for on, false for off

	
	public Printer() {
		powerState = false;
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

	public void print(String s) {
		System.out.println(s);
	}
}
