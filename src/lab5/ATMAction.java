package lab5;
import java.util.*;

public class ATMAction {

	Bank atmBank = new Bank();
	Customer newCustomer;
	
	public ATMAction() {
		// STUB
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
		if (!testAccount.validate()) {
			System.out.println("Invalid account entered: " + cardNumber);
		} else {
			do {
				System.out.print("Do you want to (W)ithdrawl or (D)eposit? ");
				choice = stdIn.next();
			} while (!choice.toLowerCase().equals("w") && !choice.toLowerCase().equals("d"));
			// ASK FOR PIN THEN VALIDATE
			System.out.print("Input PIN number: ");
			int pinNum = stdIn.nextInt();
			testAccount.setPIN(pinNum);
			atmBank.createAccount(testAccount.getAccountNumber(), testAccount.getPIN(), 0);
			if (!atmBank.validate(testAccount)) {
				System.out.println("Invalid PIN entered: " + pinNum);
			} else {
				// ASK FOR AMOUNT
				System.out.print("Enter amount: $");
				int amount = stdIn.nextInt();
				// EITHER DEPOSIT OR WITHDRAWL
				if (choice.toLowerCase().equals("w")) {
					atmBank.getAccount(cardNumber).withdraw(amount);
				} else {
					atmBank.getAccount(cardNumber).deposit(amount);
				}
				// PRINT
			}
		}
	}
}
