package lab5;

import java.util.*;

public class Bank {

	ArrayList<Account> accounts = new ArrayList<Account>();
	String bankName;

	public Bank() {
		// STUB
	}

	public Bank(String name) {
		bankName = name;
	}

	public boolean validate(Account account) {
		boolean valid = false;
		for (int i = 0; i < accounts.size(); i++) {
			if (account.getAccountNumber() == (accounts.get(i)).getAccountNumber()) {
				valid = true;
			}
		}
		return valid;
	}

	public void createAccount(int accountNum, int pinNum, int balance) {
		accounts.add(new Account(accountNum, pinNum, balance));
	}

	public Account getAccount(int accountNum) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accountNum == (accounts.get(i)).getAccountNumber()) {
				return (Account) accounts.get(i);
			}
		}
		return null;
	}

	public void addAccount(Account newAccount) {
		accounts.add(newAccount);
	}

}
