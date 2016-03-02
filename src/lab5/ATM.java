package lab5;

public class ATM {

	String name;

	public ATM() {

	}

	public ATM(String name) {
		this.name = name;
	}

	public void start() {
		ATMAction newAction = new ATMAction();
		newAction.execute();
	}

}
