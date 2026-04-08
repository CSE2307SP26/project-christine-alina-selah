package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import main.BankAccount;

public class ZelleTransferTest {

    private BankAccount account;

    @Before
    public void setUp() {
        // Create a new account before each test
        account = new BankAccount("testUser", "1234", "checking", 1000.0);
    }

    @Test
    public void testSendZelleSuccess() {
        double amountToSend = 200.0;
        double initialBalance = account.getBalance();

        // Call the method directly for testing
        account.withdraw(amountToSend);

        // Check if balance decreased correctly
        assertEquals(initialBalance - amountToSend, account.getBalance(), 0.01);
    }

    @Test
    public void testSendZelleZeroAmount() {
        double amountToSend = 0.0;
        double initialBalance = account.getBalance();

        account.withdraw(amountToSend);

        // Balance should remain unchanged
        assertEquals(initialBalance, account.getBalance(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendZelleNegativeAmount() {
        double amountToSend = -50.0;
        account.withdraw(amountToSend);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendZelleInsufficientFunds() {
        double amountToSend = 1500.0;
        account.withdraw(amountToSend);
    }

    @Test
    public void testSendZelleMultipleTimes() {
        double firstAmount = 100.0;
        double secondAmount = 200.0;
        double initialBalance = account.getBalance();

        account.withdraw(firstAmount);
        account.withdraw(secondAmount);

        assertEquals(initialBalance - firstAmount - secondAmount, account.getBalance(), 0.01);
    }
}
