package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    private BankAccount account;
    private BankAccount account2;
    private BankAccount account3;

    @BeforeEach
    public void setup() {
        account = new BankAccount("testuser", "1234", "checking", 0);
        account2 = new BankAccount("account1", "1234", "checking", 0);
        account3 = new BankAccount("account2", "1234", "checking", 0);
        account2.deposit(50);
        
    }

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 0);
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 0);
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }
    }

    @Test
    public void testSmallWithdrawalNotSuspicious() {
        BankAccount a = new BankAccount("acct", "1234", "checking", 100);
        assertDoesNotThrow(() -> a.withdrawal(a, 10));
    }

    public void testWithdrawalValidAmount() {
        account.deposit(50);
        account.withdrawal(account, 25);
        assertEquals(25, account.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawalWithInsufficientFund() {
       
        account.deposit(15);
        try {
            account.withdrawal(account, 25);
            fail();
        } catch (IllegalArgumentException e) {

        }
    } 

    @Test
    public void testWithdrawalInvalidAmount() {
        try {
            account.withdrawal(account, -10);
            fail();
        }catch(IllegalArgumentException e) {
            
        }

    }

    @Test
    public void testTransferToAnotherAccount() {
        account2.transfer(account2,account3, 20);
        assertEquals(30, account2.getBalance(), 0.01);
    }

    @Test
    public void testTransferToNullAccount() {
        try {
            account2.transfer(account2,null, 20);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testTransferWhenInsufficientFund() {
        try {
            account2.transfer(account2, account3, 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testTransferToSameAcccount() {
        try {
            account2.transfer(account2, account2, 20);
        } catch (IllegalArgumentException e) {
            
        }
    }

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

        @Test
    public void testCorrectPassword() {
        BankAccount account = new BankAccount("Bob", "1234", "checking", 0);
        assertTrue(account.checkPassword("1234"));
    }

    @Test
    public void testWrongPassword() {
        BankAccount account = new BankAccount("Bob", "1234", "checking", 0);
        assertFalse(account.checkPassword("2342"));
    }

    @Test
    public void testEmptyAccountName() {
        try {
            new BankAccount(" ", "1234", "checking", 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testEmptyPassword() {
        try {
            new BankAccount("bob", "", "checking", 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
    

}
