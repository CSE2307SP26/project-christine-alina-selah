package main;

import java.util.HashMap;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 2;
    private static final int MAX_SELECTION = 5;

    private BankAccount userAccount;
    private Scanner keyboardInput;

    // Adding hashmap in order to keep track of multiple bank accounts
    private HashMap<String, BankAccount> accounts;
    private String currentAccountName;
    private AccountCreation accountCreation;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);

        // Constructing hashmap, setting default bank account, and adding said bank
        // account to hashmap
        this.accounts = new HashMap<>();
        this.currentAccountName = "default";
        this.accounts.put(this.currentAccountName, this.userAccount);

        this.accountCreation = new AccountCreation();
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        System.out.println("1. Make a deposit");
        System.out.println("2. Exit the app");

        System.out.println("5. Create a new account");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
            keyboardInput.nextLine();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 5:
                performCreateAccount();
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        userAccount.deposit(depositAmount);
    }

    // Method to create a new account
    public void performCreateAccount() {
        try {
            // Prompting user for account name
            System.out.println("What would you like to name your new account?");
            String accountName = keyboardInput.nextLine();

            // Prompting user for initial account balance
            System.out.print("How much would you like to deposit: ");
            double initialBalance = keyboardInput.nextDouble();
            keyboardInput.nextLine();

            BankAccount newAccount = accountCreation.createAccount(accountName, initialBalance, accounts);

            this.userAccount = newAccount;
            this.currentAccountName = accountName;

            System.out.println(
                    "You are now using the " + accountName + " account with a balance of $" + newAccount.getBalance());

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}