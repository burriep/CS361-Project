package lab5;
import java.util.*;

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
		boolean valid = false;
		int length = (int)(Math.log10(accountNumber)+1);
		valid = length == 4;
		System.out.print("Input PIN number: ");
		Scanner stdIn = new Scanner(System.in);
		int pinNum = stdIn.nextInt();
		if (pinCode == pinNum) {
			return true;
		} else {
			return false;
		}
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
		if (balance > 0) {
			balance += amount;
		}
	}

	public void withdraw(int amount) {
		if (amount > 0) {
			if (balance - amount > 0) {
				balance -=amount;
			} else {
				System.out.println("Insufficient funds to withdraw: $" + amount);
			}
		}
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
