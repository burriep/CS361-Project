package lab5;
public class Account {

	int accountNumber;
	int pinCode;
	int balance;

	public Account() {

	}

	public Account(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public Account(int accountNumber, int pinCode, int balance) {
		this.accountNumber = accountNumber;
		this.pinCode = pinCode;
		this.balance = balance;
	}

	public boolean validate() {
		int length = (int)(Math.log10(accountNumber)+1);
		return length == 4 ? true : false;
	}
	
	public void setPIN(int pin) {
		pinCode = pin;
	}
	
	public int getPIN() {
		return pinCode;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public void deposit(int amount) {
		balance += amount;
	}

	public void withdraw(int amount) {
		balance -= amount;
	}
	
	public int getBalance() {
		return balance;
	}
}
