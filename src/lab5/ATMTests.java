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
		bank = new Bank("The Bank");
		myATM = new ATM("The ATM");
		account1 = new Account(1234, 6789, 80);
		account2 = new Account(6789, 4321, 60);
		// setup account 1234
		bank.addAccount(account1);
		// setup account 6789
		bank.addAccount(account2);
	}

	@Test
	public void TestAcctValidation() {
		// test successful validation on account 1234
		assertTrue(bank.validate(account1));
		// test successful validation on account 6789
		assertTrue(bank.validate(account2));
		// test unsuccessful validation on account 2345
		Account account3 = new Account(2345, 9999, 60);
		assertFalse(bank.validate(account3));
	}

	@Test
	public void TestDeposit() {
		// test successful deposit of $20 to account 1234
		account1.deposit(20);
		assertEquals(100, account1.getBalance());
		// test successful deposit of $0 to account 6789
		account2.deposit(0);
		assertEquals(60, account2.getBalance());
		// test unsuccessful deposit of -$20 to account 1234
		account1.deposit(-20);
		assertEquals(60, account2.getBalance());
	}

	@Test
	public void TestWithdraw() {
		// test successful withdraw of $20 to account 1234
		account1.withdraw(20);
		assertEquals(60, account1.getBalance());
		// test successful deposit of $0 to account 6789
		account2.withdraw(0);
		assertEquals(60, account2.getBalance());
		// test unsuccessful deposit of -$20 to account 1234
		account1.withdraw(-20);
		assertEquals(60, account2.getBalance());
	}
}
