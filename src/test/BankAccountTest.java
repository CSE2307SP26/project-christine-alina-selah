package test;

import main.BankAccount;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    private BankAccount account;
    private BankAccount account2;
    private BankAccount account3;
    private BankAccount account4;
    private BankAccount account5;

    @BeforeEach
    public void setup() {
        account = new BankAccount("testuser", "1234", "checking", 0);
        account2 = new BankAccount("account1", "1234", "checking", 0);
        account3 = new BankAccount("account2", "1234", "checking", 0);
        account4 = new BankAccount("Alice", "1234", "checking", 100.0);
        account5 = new BankAccount("testUser", "1234", "checking", 1000.0);
        account2.deposit(50);
        
    }

    @Test
    public void testBuyingSameStockUpdatesHolding() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 1000);
        testAccount.buyStock("AAPL", 1, 100);
        testAccount.buyStock("AAPL", 2, 200);

        String summary = testAccount.getPortfolioSummary();

        assertTrue(summary.contains("AAPL"));
        assertTrue(summary.contains("3.0 shares"));
        assertTrue(summary.contains("166.666"));
    }

    @Test
    public void testEmptyPortfolioSummary() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 500);

        assertEquals("No stocks owned.", testAccount.getPortfolioSummary());
    }

    @Test
    public void testBuyStockInvalidShares() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 500);

        try {
            testAccount.buyStock("AAPL", 0, 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testBuyStockInsufficientFunds() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 100);

        try {
            testAccount.buyStock("AAPL", 2, 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testBuyStockEmptyTicker() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 500);

        try {
            testAccount.buyStock("", 2, 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testBuyStockAddsHoldingToPortfolio() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 500);
        testAccount.buyStock("AAPL", 2, 100);

        String summary = testAccount.getPortfolioSummary();

        assertTrue(summary.contains("AAPL"));
        assertTrue(summary.contains("2.0 shares"));
    }

    @Test
    public void testBuyStockReducesBalance() {
        BankAccount testAccount = new BankAccount("testUser", "1234", "checking", 500);
        testAccount.buyStock("AAPL", 2, 100);

        assertEquals(300, testAccount.getBalance(), 0.01);
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

    @Test
    public void testApplyForLoanValidAmount() {
        account4.applyForLoan(500);

        assertEquals(600.0, account4.getBalance(), 0.01);
        assertEquals(500.0, account4.getLoanBalance(), 0.01);
    }

    @Test
    public void testApplyForLoanInvalidAmount() {
        try {
            account4.applyForLoan(-100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testApplyForLoanAmountTooLarge() {
        try {
            account4.applyForLoan(20000);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testApplyForLoanWithLowCreditScore() {
        account4.decreaseCreditScore(100);

        try {
            account4.applyForLoan(500);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testMakeLoanPaymentValidAmount() {
        account4.applyForLoan(500);
        account4.makeLoanPayment(200);

        assertEquals(400.0, account4.getBalance(), 0.01);
        assertEquals(300.0, account4.getLoanBalance(), 0.01);
    }

    @Test
    public void testMakeLoanPaymentInvalidAmount() {
        account4.applyForLoan(500);

        try {
            account4.makeLoanPayment(-50);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testMakeLoanPaymentWithNoLoanBalance() {
        try {
            account4.makeLoanPayment(50);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testMakeLoanPaymentWithInsufficientBalance() {
        account4.applyForLoan(500);
        account4.withdrawal(account4, 550);

        try {
            account4.makeLoanPayment(100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testMakeLoanPaymentGreaterThanLoanBalance() {
        account4.applyForLoan(200);

        try {
            account4.makeLoanPayment(500);
            fail();
        } catch (IllegalArgumentException e) {}

    }

    @Test
    public void testIncreaseCreditScore() {
        account4.increaseCreditScore(20);
        assertEquals(670, account4.getCreditScore());
    }

    @Test
    public void testDecreaseCreditScore() {
        account4.decreaseCreditScore(30);
        assertEquals(620, account4.getCreditScore());
    }

    @Test
    public void testSendZelleSuccess() {
        double amountToSend = 200.0;
        double initialBalance = account5.getBalance();

        // Call the method directly for testing
        account5.withdrawal(account5, amountToSend);

        // Check if balance decreased correctly
        assertEquals(initialBalance - amountToSend, account5.getBalance(), 0.01);
    }

    @Test
    public void testSendZelleZeroAmount() {
        double amountToSend = 0.0;
        double initialBalance = account5.getBalance();

        try {
            account5.withdrawal(account5, amountToSend);
            fail();
        } catch(IllegalArgumentException e) {}

        // Balance should remain unchanged
        assertEquals(initialBalance, account5.getBalance(), 0.01);
    }
    @Test
    public void testSendZelleNegativeAmount() {
        double amountToSend = -50.0;

        assertThrows(IllegalArgumentException.class, () -> {
            account5.withdrawal(account5, amountToSend);
        });
    }

    @Test
    public void testSendZelleInsufficientFunds() {
        double amountToSend = 1500.0;

        assertThrows(IllegalArgumentException.class, () -> {
            account5.withdrawal(account5, amountToSend);
        });
    }   

    @Test
    public void testSendZelleMultipleTimes() {
        double firstAmount = 100.0;
        double secondAmount = 200.0;
        double initialBalance = account5.getBalance();

        account5.withdrawal(account5, firstAmount);
        account5.withdrawal(account5, secondAmount);

        assertEquals(initialBalance - firstAmount - secondAmount, account5.getBalance(), 0.01);
    }
    

}
