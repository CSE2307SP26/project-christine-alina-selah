package test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import main.BankAccount;

public class LoginTest {

    @Test
    public void testCorrectPassword() {
        BankAccount account = new BankAccount("Bob", "1234");
        assertTrue(account.checkPassword("1234"));
    }

    @Test
    public void testWrongPassword() {
        BankAccount account = new BankAccount("Bob", "1234");
        assertFalse(account.checkPassword("2342"));
    }

    @Test
    public void testEmptyAccountName() {
        try {
            new BankAccount(" ", "1234");
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testEmptyPassword() {
        try {
            new BankAccount("bob", "");
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
}