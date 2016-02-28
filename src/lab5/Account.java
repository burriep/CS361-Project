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
		// STUB
		return false;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public void deposit(int amount) {
		balance += amount;
	}

	public void withdrawl(int amount) {
		balance -= amount;
	}
}
