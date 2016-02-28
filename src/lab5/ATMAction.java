package lab5;
import java.util.*;

public class ATMAction {

	Bank atmBank = new Bank();

	public ATMAction() {
		// STUB
	}

	public void execute() {
		String choice;
		Scanner stdIn = new Scanner(System.in);
		System.out.print("Input card number: ");
		int cardNumber = stdIn.nextInt();
		Customer newCustomer = new Customer(cardNumber);
		if (!atmBank.validate(newCustomer.getCustomerAccount())) {
			System.out.println("Invalid account entered: " + cardNumber);
		} else {
			do {
				System.out.print("Do you want to (W)ithdrawl or (D)eposit? ");
				choice = stdIn.next();
			} while (!choice.toLowerCase().equals("w") && !choice.toLowerCase().equals("d"));
			// ASK FOR PIN THEN VALIDATE
			System.out.print("Input PIN number: ");
			int pinNum = stdIn.nextInt();

			// ASK FOR AMOUNT
			// EITHER DEPOSIT OR WITHDRAWL
			// PRINT
		}
	}

	public void depost(int amount) {
		// STUB
	}

	public void withdrawl(int amount) {
		// STUB
	}
}
