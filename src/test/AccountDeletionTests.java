package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import main.MainMenu;

public class AccountDeletionTests {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @AfterEach
    public void restoreSystemStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
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
    public void testCloseLastAccountThrowsException() {
        MainMenu menu = new MainMenu();
        menu.createAccount("Only", "1234", "checking", 100.0);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            menu.closeAccount("Only");
        });

        assertEquals(
                "You cannot close your last account. Please create a new one before trying again.",
                e.getMessage());
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
        String input = "Checking\nSavings\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainMenu menu = new MainMenu();
        menu.createAccount("Checking", "1234", "checking", 100.0);
        menu.createAccount("Savings", "1234", "checking", 200.0);


        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performCloseAccount();

        assertFalse(menu.getAccounts().containsKey("Checking"));
        assertTrue(menu.getAccounts().containsKey("Savings"));
        assertEquals("Savings", menu.getCurrentAccountName());

        String console = output.toString();
        assertTrue(console
                .contains("You are currently using this account and cannot close it before switching to a new one."));
        assertTrue(console.contains("You are now using the Savings account."));
        assertTrue(console.contains("Account Checking has been closed."));
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