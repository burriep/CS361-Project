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

	@Before
	public static void setup() {
		bank = new Bank("The Bank");
		myATM = new ATM("The ATM");
		bank.createAccount(1234, 6789, 80);
		bank.createAccount(6789, 4321, 60);
		// setup account 1234
		// setup account 6789
	}

	@Test
	public void TestWithdrawal() {
		assertTrue(bank.validate(1234)); // prompts user for number
		assertTrue(bank.withdraw());
		// withdraw 20 from 1234
		// withdraw 80 from 1234
	}

	public void TestAcctValidation() {
		// test failed validation on account 6789
		assertTrue(bank.validate(6789));
		myATM.withdraw();
		myATM.enterPIN();
	}

	public void TestDeposit() {
		// test successful deposit of $20 to account 6789
	}
}
