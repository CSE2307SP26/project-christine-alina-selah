import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import main.MainMenu;

public class MainMenuStockTests {

    private final PrintStream originalOut = System.out;

    @AfterEach
    public void restoreSystemStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testPerformViewPortfolioRequiresLogin() {
        MainMenu menu = new MainMenu();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performViewPortfolio();

        assertTrue(output.toString().contains("You must log in first."));
    }

    @Test
    public void testPerformViewPortfolioEmptyPortfolio() {
        MainMenu menu = new MainMenu();
        menu.createAccount("Investor", "1234", "checking", 500.0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performViewPortfolio();

        String console = output.toString();
        assertTrue(console.contains("Portfolio:"));
        assertTrue(console.contains("No stocks owned."));
    }

    @Test
    public void testPerformViewPortfolioShowsOwnedStock() {
        MainMenu menu = new MainMenu();
        menu.createAccount("Investor", "1234", "checking", 500.0);
        menu.getAccounts().get("Investor").buyStock("AAPL", 2, 100.0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        menu.performViewPortfolio();

        String console = output.toString();
        assertTrue(console.contains("Portfolio:"));
        assertTrue(console.contains("AAPL"));
        assertTrue(console.contains("2.0 shares"));
    }
}