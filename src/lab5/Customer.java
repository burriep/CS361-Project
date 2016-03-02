package lab5;

public class Customer {

	Account customerAccount;
	Card customerCard;

	public Customer() {

	}

	public void addCustomerCard(Card newCard) {
		customerCard = newCard;
	}

	public int getCustomerCard() {
		return customerCard.getCardNumber();
	}

	public void addCustomerAccount(Account customerAccount) {
		this.customerAccount = customerAccount;
	}

	public Account getCustomerAccount() {
		return customerAccount;
	}

}
