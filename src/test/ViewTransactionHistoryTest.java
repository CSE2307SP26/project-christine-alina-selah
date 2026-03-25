package test;

import main.ViewTransactionHistory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import main.BankAccount;

/// to format code: shift + alt + f

public class ViewTransactionHistoryTest {

    @Test

    public void testEmptyHistory() {
        ViewTransactionHistory view = new ViewTransactionHistory();
        assertTrue(view.viewHistory().isEmpty());
    }

    @Test
    public void testSingleEntry() {
        ViewTransactionHistory view = new ViewTransactionHistory();
        view.record("Deposited $50");

        assertEquals(1, view.viewHistory().size());
        assertEquals("Deposited $50", view.viewHistory().get(0));
    }

    @Test
    public void testMultipleEntries() {
        ViewTransactionHistory view = new ViewTransactionHistory();
        view.record("Deposited $10");
        view.record("Deposited $20");

        assertEquals(2, view.viewHistory().size());
    }
}
