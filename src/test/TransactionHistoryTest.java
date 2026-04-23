package test;

import main.TransactionHistory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionHistoryTest {

    @Test
    public void testRecordAddsEntry() {
        TransactionHistory h = new TransactionHistory();
        h.record("Deposited $20");
        assertEquals(1, h.viewHistory().size());
    }

    @Test
    public void testViewHistoryReturnsList() {
        TransactionHistory h = new TransactionHistory();
        h.record("Test");
        assertTrue(h.viewHistory().contains("Test"));
    }

    @Test
    public void testSearchFindsMatch() {
        TransactionHistory h = new TransactionHistory();
        h.record("Withdrew $10");
        assertEquals(1, h.search("withdrew").size());
    }

    @Test
    public void testRecordWithCategoryAddsCategory() {
        TransactionHistory h = new TransactionHistory();
        h.record("Income", "Deposited $20");
        assertTrue(h.viewHistory().get(0).contains("[Income]"));
    }

    @Test
    public void testRecordWithCategoryStoresEntry() {
        TransactionHistory h = new TransactionHistory();
        h.record("Gift", "Deposited $10");
        assertTrue(h.viewHistory().get(0).contains("Deposited $10"));
    }

    @Test
    public void testSearchFindsCategorizedEntry() {
        TransactionHistory h = new TransactionHistory();
        h.record("Savings", "Deposited $100");
        assertEquals(1, h.search("100").size());
    }

}
