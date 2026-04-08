package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import main.BankAccount;
import main.MainMenu;

public class AccountCreationTests {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @AfterEach
    public void restoreSystemStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testCreateAccountSuccess() {
        MainMenu menu = new MainMenu();

        menu.createAccount("TestUser", "1234", "checking", 100);

        assertTrue(menu.getAccounts().containsKey("TestUser"));
        BankAccount account = menu.getAccounts().get("TestUser");
        assertNotNull(account);
        assertEquals(100.0, account.getBalance(), 0.001);
        assertEquals("TestUser", menu.getCurrentAccountName());
    }

    @Test
    public void testCreateAccountEmptyNameThrowsException() {
        MainMenu menu = new MainMenu();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            menu.createAccount("", "1234", "checking", 100.0);
        });

        assertEquals("Account name cannot be empty.", e.getMessage());
    }

    @Test
    public void testCreateAccountDuplicateThrowsException() {
        MainMenu menu = new MainMenu();
        menu.createAccount("TestUser", "1234", "checking", 100.0);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            menu.createAccount("TestUser", "1234", "checking", 100.0);
        });

        assertEquals("Account name already exists.", e.getMessage());
    }

    @Test
    public void testCreateAccountNegativeBalanceThrowsException() {
        MainMenu menu = new MainMenu();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            menu.createAccount("TestUser", "1234", "checking", -10.0);
        });

        assertEquals("Initial balance cannot be negative.", e.getMessage());
    }

    @Test
    public void testPerformCreateAccountSuccess() {
        String input = "Checking\n1234\nchecking\n150.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        MainMenu menu = new MainMenu();
        menu.performCreateAccount();

        assertTrue(menu.getAccounts().containsKey("Checking"));
        assertEquals(150.0, menu.getAccounts().get("Checking").getBalance(), 0.001);
        assertEquals("Checking", menu.getCurrentAccountName());

        String console = output.toString();
        assertTrue(console.contains("What would you like to name your new account?"));
        assertTrue(console.contains("How much would you like to deposit: "));
        assertTrue(console.contains("You are now using the Checking account with a balance of $150.0"));
    }

    @Test
    public void testPerformCreateAccountDuplicatePrintsError() {
        String input = "Checking\n1234\nchecking\n75.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainMenu menu = new MainMenu();
        menu.createAccount("Checking", "1234", "checking", 100.0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performCreateAccount();

        assertTrue(output.toString().contains("Account name already exists."));
    }
}