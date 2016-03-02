package lab5;
public class Card {

	private int accountNumber;

	public Card() {
		System.out.println("Cards must have a number.");
	}

	public Card(int acctNum) {
		accountNumber = acctNum;
	}
	
	public int getCardNumber() {
		return accountNumber;
	}

}
