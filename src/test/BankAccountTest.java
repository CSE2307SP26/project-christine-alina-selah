package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

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

}
