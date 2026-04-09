package test;

import main.BankAccount;
import main.AccountSummaryReport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountSummaryReportTest {

    @Test
    public void testSummaryContainsName() {
        BankAccount a = new BankAccount("main", "1234", "checking", 50);
        AccountSummaryReport r = new AccountSummaryReport(a);
        assertTrue(r.generateSummary().contains("main"));
    }

    @Test
    public void testSummaryContainsType() {
        BankAccount a = new BankAccount("save", "1234", "savings", 100);
        AccountSummaryReport r = new AccountSummaryReport(a);
        assertTrue(r.generateSummary().contains("savings"));
    }

    @Test
    public void testSummaryContainsBalance() {
        BankAccount a = new BankAccount("acct", "1234", "checking", 75);
        AccountSummaryReport r = new AccountSummaryReport(a);
        assertTrue(r.generateSummary().contains("75"));
    }
}