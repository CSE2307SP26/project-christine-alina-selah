package test;

import main.CheckBankAccount;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CheckBankAccountTest {

    @Test
    void testCheckBalance() {
        CheckBankAccount account = new CheckBankAccount(150.00);
        assertEquals(150.00, account.checkBalance());
    }

    @Test
    void testCheckZeroBalance() {
        CheckBankAccount account = new CheckBankAccount(0.0);
        assertEquals(00, account.checkBalance());
    }

    @Test
    void testNegativeBalance() {
        CheckBankAccount account = new CheckBankAccount(-50.0);
        assertEquals(-50.0, account.checkBalance());
    }
}
