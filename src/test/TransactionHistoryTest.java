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
}
