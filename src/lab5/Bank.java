package lab5;
public class Bank {

	Account accountArray[] = { new Account(1234, 6789, 80), new Account(6789, 4321, 60) };

	public Bank() {
		// STUB
	}

	public boolean validate(Account account) {
		boolean valid = false;
		for (int i = 0; i < accountArray.length; i++) {
			if (account.getAccountNumber() == accountArray[i].getAccountNumber()) {
				valid = true;
			}
		}
		return valid;
	}

}
