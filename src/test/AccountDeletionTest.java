package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.AccountDeletion;
import main.BankAccount;

public class AccountDeletionTest {

    private AccountDeletion accountDeletion;
    private HashMap<String, BankAccount> accounts;

    @BeforeEach
    public void setup() {
        accountDeletion = new AccountDeletion();
        accounts = new HashMap<>();

        BankAccount defaultAccount = new BankAccount();
        defaultAccount.deposit(100.0);

        BankAccount savingsAccount = new BankAccount();
        savingsAccount.deposit(200.0);

        accounts.put("default", defaultAccount);
        accounts.put("savings", savingsAccount);
    }

    @Test
    public void testDeleteAccountSuccess() {
        BankAccount remainingAccount = accountDeletion.deleteAccount("savings", "default", accounts);

        assertFalse(accounts.containsKey("savings"));
        assertTrue(accounts.containsKey("default"));
        assertNotNull(remainingAccount);
        assertEquals(1, accounts.size());
    }

    @Test
    public void testDeleteNonexistentAccountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountDeletion.deleteAccount("checking", "default", accounts);
        });
    }

    @Test
    public void testDeleteLastAccountThrowsException() {
        HashMap<String, BankAccount> oneAccountMap = new HashMap<>();
        BankAccount onlyAccount = new BankAccount();
        onlyAccount.deposit(50.0);
        oneAccountMap.put("default", onlyAccount);

        assertThrows(IllegalArgumentException.class, () -> {
            accountDeletion.deleteAccount("default", "other", oneAccountMap);
        });
    }

    @Test
    public void testDeleteCurrentAccountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountDeletion.deleteAccount("default", "default", accounts);
        });
    }

    @Test
    public void testDeleteEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountDeletion.deleteAccount("", "default", accounts);
        });
    }

    @Test
    public void testDeleteBlankNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountDeletion.deleteAccount("   ", "default", accounts);
        });
    }
}