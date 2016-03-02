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

	@Test
	public void TestAcctValidation() {
		// test failed validation on account 6789
		assertTrue(bank.validate(account1));
	}

	@Test
	public void TestDeposit() {
		// test successful deposit of $20 to account 1234
		account1.deposit(20);
		assertEquals(account1.getBalance(), 100);
		// test successful deposit of $0 to account 6789
		account2.deposit(0);
		assertEquals(account2.getBalance(), 60);
		// test unsuccessful deposit of -$20 to account 1234
		account1.deposit(-20);
		assertEquals(account2.getBalance(), 60);
	}
	
	@Test
	public void TestWithdraw() {
		account1.setBalance(80);
		account2.setBalance(60);
		// test successful withdraw of $20 to account 1234
		account1.withdraw(20);
		assertEquals(account1.getBalance(), 100);
		// test successful deposit of $0 to account 6789
		account2.withdraw(0);
		assertEquals(account2.getBalance(), 60);
		// test unsuccessful deposit of -$20 to account 1234
		account1.withdraw(-20);
		assertEquals(account2.getBalance(), 60);
	}
}
