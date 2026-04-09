package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.junit.Assert.fail;

import main.BankAccount;

public class AccountTypeTest {
    
    @Test
    public void testCheckingAccountType() {
        BankAccount account = new BankAccount("bob", "1234", "checking", 0);
        assertEquals("checking", account.getAccountType().toLowerCase());
    }

    @Test
    public void testSavingsAccountType() {
        BankAccount account = new BankAccount("bob", "1234", "savings", 0);
        assertEquals("savings", account.getAccountType().toLowerCase());
    }

    @Test
    public void testInvalidAccountType() {
        try {
            BankAccount account = new BankAccount("bob", "1234", "random", 0);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testApplyIntrestSavings() {
        BankAccount account = new BankAccount("bob", "1234", "savings", 0);
        account.deposit(100);
        account.applyIntrest();
        assertEquals(105, account.getBalance(), 0.01);
    }

    @Test
    public void testApplyIntrestChecking() {
        BankAccount account = new BankAccount("bob", "1234", "checking", 0);
        account.deposit(100);
        try {
            account.applyIntrest();
            fail();
        } catch (IllegalArgumentException e) {

        }

    }

}
