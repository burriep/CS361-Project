package lab5;
public class Customer {

	Account customerAccount;

	public Customer() {
		// STUB
	}

	public Customer(int customerNumber) {
		Card customerCard = new Card(customerNumber);
		customerAccount = new Account(customerNumber);
	}

	public Account getCustomerAccount() {
		return customerAccount;
	}

}
