package test;

import main.ViewTransactionHistory;
import main.SearchTransactionHistory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SearchTransactionsTest {

    @Test
    public void testFindsMatch() {
        ViewTransactionHistory h = new ViewTransactionHistory();
        h.record("Deposited $20");
        SearchTransactionHistory s = new SearchTransactionHistory(h);
        assertEquals(1, s.search("deposit").size());
    }

    @Test
    public void testNoMatches() {
        ViewTransactionHistory h = new ViewTransactionHistory();
        h.record("Deposited $20");
        SearchTransactionHistory s = new SearchTransactionHistory(h);
        assertTrue(s.search("withdraw").isEmpty());
    }

    @Test
    public void testEmptyKeywordReturnsEmptyList() {
        ViewTransactionHistory h = new ViewTransactionHistory();
        h.record("Deposited $20");
        SearchTransactionHistory s = new SearchTransactionHistory(h);
        assertTrue(s.search("").isEmpty());
    }
}