package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.BankAccount;
import main.StatementGenerator;
import main.TransactionHistory;

public class StatementGeneratorTest {

    @Test
    public void testConstructorNullAccountThrowsException() {
        TransactionHistory history = new TransactionHistory();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new StatementGenerator(null, history);
        });

        assertEquals("Account cannot be null", e.getMessage());
    }

    @Test
    public void testConstructorNullHistoryThrowsException() {
        BankAccount account = new BankAccount("Alice", "1234", "checking", 100.0);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new StatementGenerator(account, null);
        });

        assertEquals("Transaction History cannot be null", e.getMessage());
    }

    @Test
    public void testGenerateStatementForRegularAccountWithNoTransactions() {
        BankAccount account = new BankAccount("Alice", "1234", "checking", 100.0);
        TransactionHistory history = new TransactionHistory();

        StatementGenerator generator = new StatementGenerator(account, history);
        String statement = generator.generateStatement();

        assertTrue(statement.contains("BANK STATEMENT"));
        assertTrue(statement.contains("Account Name: Alice"));
        assertTrue(statement.contains("Account Type: checking"));
        assertTrue(statement.contains("Current Balance: $100.00"));
        assertTrue(statement.contains("Joint Account: No"));
        assertTrue(statement.contains("Transaction History:"));
        assertTrue(statement.contains("No transactions found."));
    }

    @Test
    public void testGenerateStatementForJointAccount() {
        BankAccount account = new BankAccount("Alice", "Bob", "1234", "savings", 250.0);
        TransactionHistory history = new TransactionHistory();

        StatementGenerator generator = new StatementGenerator(account, history);
        String statement = generator.generateStatement();

        assertTrue(statement.contains("Account Name: Alice"));
        assertTrue(statement.contains("Account Type: savings"));
        assertTrue(statement.contains("Current Balance: $250.00"));
        assertTrue(statement.contains("Joint Account: Yes"));
        assertTrue(statement.contains("Second Owner: Bob"));
    }

    @Test
    public void testGenerateStatementWithTransactions() {
        BankAccount account = new BankAccount("Alice", "1234", "checking", 300.0);
        TransactionHistory history = new TransactionHistory();

        history.record("Deposited $100");
        history.record("Transferred $50 to Bob");

        StatementGenerator generator = new StatementGenerator(account, history);
        String statement = generator.generateStatement();

        assertTrue(statement.contains("1. Deposited $100"));
        assertTrue(statement.contains("2. Transferred $50 to Bob"));
        assertFalse(statement.contains("No transactions found."));
    }

    @Test
    public void testGenerateStatementIncludesGeneratedLine() {
        BankAccount account = new BankAccount("Alice", "1234", "checking", 100.0);
        TransactionHistory history = new TransactionHistory();

        StatementGenerator generator = new StatementGenerator(account, history);
        String statement = generator.generateStatement();

        assertTrue(statement.contains("Generated: "));
    }
}
