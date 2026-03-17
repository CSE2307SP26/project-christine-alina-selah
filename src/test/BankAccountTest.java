package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testTransferToAnotherAccount() {
        BankAccount testAccount = new BankAccount();
        BankAccount destinationAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.transfer(destinationAccount, 20);
        assertEquals(30, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testTransferToNullAccount() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        try {
            testAccount.transfer(null, 20);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testTransferWhenInsufficientFund() {
        BankAccount testAccount = new BankAccount();
        BankAccount destinationAccount = new BankAccount();
        testAccount.deposit(50);
        try {
            testAccount.transfer(destinationAccount, 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
}
