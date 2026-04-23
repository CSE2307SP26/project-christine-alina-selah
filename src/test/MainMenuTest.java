package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import main.BankAccount;
import main.MainMenu;

public class MainMenuTest {

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

    @Test
    public void testCloseAccountSuccess() {
        MainMenu menu = new MainMenu();
        menu.createAccount("A1", "1234", "checking", 100.0);
        menu.createAccount("A2", "1234", "checking", 200.0);

        menu.closeAccount("A1");

        assertFalse(menu.getAccounts().containsKey("A1"));
        assertTrue(menu.getAccounts().containsKey("A2"));
    }

    @Test
    public void testCloseNonexistentAccountThrowsException() {
        MainMenu menu = new MainMenu();
        menu.createAccount("A1", "1234", "checking", 100.0);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            menu.closeAccount("Fake");
        });

        assertEquals("Account Fake does not exist.", e.getMessage());
    }

    @Test
    public void testPerformCloseAccountSuccess() {
        String input = "A1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainMenu menu = new MainMenu();
        menu.createAccount("A1", "1234", "checking", 100.0);
        menu.createAccount("A2", "1234", "checking", 200.0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performCloseAccount();

        assertFalse(menu.getAccounts().containsKey("A1"));
        assertTrue(menu.getAccounts().containsKey("A2"));
        assertTrue(output.toString().contains("Account A1 has been closed."));
    }

    @Test
    public void testPerformCloseAccountSwitchesThenCloses() {
        String input = "Savings\nChecking\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainMenu menu = new MainMenu();
        menu.createAccount("Checking", "1234", "checking", 100.0);
        menu.createAccount("Savings", "1234", "checking", 200.0);


        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performCloseAccount();

        assertFalse(menu.getAccounts().containsKey("Savings"));
        assertTrue(menu.getAccounts().containsKey("Checking"));
        assertEquals("Checking", menu.getCurrentAccountName());

        String console = output.toString();
        assertTrue(console
                .contains("You are currently using this account and cannot close it before switching to a new one."));
        assertTrue(console.contains("You are now using the Checking account."));
        assertTrue(console.contains("Account Savings has been closed."));
    }

    @Test
    public void testPerformCloseAccountEmptyNamePrintsError() {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainMenu menu = new MainMenu();
        menu.createAccount("A1", "1234", "checking", 100.0);
        menu.createAccount("A2", "1234", "checking", 200.0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performCloseAccount();

        assertTrue(output.toString().contains("Account name cannot be empty."));
    }
    
}
