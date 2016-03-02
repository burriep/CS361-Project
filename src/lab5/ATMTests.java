package lab5;
/*
 * Not In Use (Right Now)
 */
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ATMTests {
	private ATM myATM;
	private Bank bank;
	private Account account1;
	private Account account2;

	@Before
	public void setup() {
		Bank bank = new Bank("The Bank");
		ATM myATM = new ATM("The ATM");
		Account account1 = new Account(1234, 6789, 80);
		Account account2 = new Account(6789, 4321, 60);
		bank.addAccount(account1);
		bank.addAccount(account2);
		// setup account 1234
		// setup account 6789
	}

	@Test
	public void TestWithdrawal() {
		assertTrue(bank.validate(account1)); // prompts user for number
		account1.withdraw(20);
		assertEquals(account1.getBalance(), 60);
		// withdraw 20 from 1234
		// withdraw 80 from 1234
	}

	public void TestAcctValidation() {
		// test failed validation on account 6789
		assertTrue(bank.validate(account1));
		//myATM.withdraw();
		//myATM.enterPIN();
	}

	public void TestDeposit() {
		// test successful deposit of $20 to account 6789
	}
}
