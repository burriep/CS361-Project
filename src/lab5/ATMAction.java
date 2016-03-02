package lab5;

import java.util.*;

public class ATMAction {

	Bank atmBank = new Bank();
	Customer newCustomer;

	public ATMAction() {
		// STUB
		atmBank.createAccount(1234, 6789, 80);
		atmBank.createAccount(6789, 4321, 60);
	}

	public void execute() {
		Customer newCustomer = new Customer();
		String choice;
		Scanner stdIn = new Scanner(System.in);
		System.out.print("Input card number: ");
		int cardNumber = stdIn.nextInt();
		Card testCard = new Card(cardNumber);
		Customer testCustomer = new Customer();
		testCustomer.addCustomerCard(testCard);
		Account testAccount = new Account(cardNumber);
		System.out.println(testCustomer.getCustomerCard());
		if (!atmBank.validate(testAccount)) {
			System.out.println("Invalid account entered: " + cardNumber);
		} else {
			testAccount = atmBank.getAccount(cardNumber);
			do {
				System.out.print("Do you want to (W)ithdrawl or (D)eposit? ");
				choice = stdIn.next();
			} while (!choice.toLowerCase().equals("w") && !choice.toLowerCase().equals("d"));
			// ASK FOR PIN THEN VALIDATE
			if (!testAccount.validate()) {
				System.out.println("Invalid PIN entered.");
			} else {
				// ASK FOR AMOUNT
				int amount;
				do {
					System.out.print("Enter amount: $");
					amount = stdIn.nextInt();
					if (amount < 0) {
						System.out.println("Invalid amount entered.");
					}
				} while (amount < 0);
				// EITHER DEPOSIT OR WITHDRAWL
				if (choice.toLowerCase().equals("w")) {
					atmBank.getAccount(cardNumber).withdraw(amount);
				} else {
					atmBank.getAccount(cardNumber).deposit(amount);
				}
				// PRINT
				System.out.println(testAccount.getBalance());
			}
		}
	}
}
