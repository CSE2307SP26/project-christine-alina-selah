package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.AccountCreation;
import main.BankAccount;

public class AccountCreationTest {

    private AccountCreation accountCreation;
    private HashMap<String, BankAccount> accounts;

    @BeforeEach
    public void setup() {
        accountCreation = new AccountCreation();
        accounts = new HashMap<>();
    }

    @Test
    public void testCreateAccountSuccess() {
        BankAccount account = accountCreation.createAccount("TestUser", 100.0, accounts);

        assertNotNull(account);
        assertTrue(accounts.containsKey("TestUser"));
        assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    public void testDuplicateAccountThrowsException() {
        accountCreation.createAccount("TestUser", 100.0, accounts);

        assertThrows(IllegalArgumentException.class, () -> {
            accountCreation.createAccount("TestUser", 50.0, accounts);
        });
    }

    @Test
    public void testNegativeBalanceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountCreation.createAccount("TestUser", -10.0, accounts);
        });
    }

    @Test
    public void testEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountCreation.createAccount("", 50.0, accounts);
        });
    }

    @Test
    public void testBlankNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountCreation.createAccount("   ", 50.0, accounts);
        });
    }
}
