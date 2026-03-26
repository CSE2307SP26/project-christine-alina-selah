package main;

import java.util.HashMap;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 2;
    private static final int MAX_SELECTION = 6;

    private BankAccount userAccount;
    private Scanner keyboardInput;

    // Adding hashmap in order to keep track of multiple bank accounts
    private HashMap<String, BankAccount> accounts;
    private String currentAccountName;
    private AccountDeletion accountDeletion;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);

        // Constructing hashmap, setting default bank account, and adding said bank
        // account to hashmap
        this.accounts = new HashMap<>();
        this.currentAccountName = "default";
        this.accounts.put(this.currentAccountName, this.userAccount);

        this.accountDeletion = new AccountDeletion();
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        System.out.println("1. Make a deposit");
        System.out.println("2. Exit the app");

        System.out.println("6. Close an existing account");
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
            case 6:
                performCloseAccount();
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

    // Method to close an account
    public void performCloseAccount() {
        try {
            System.out.println("Which account would you like to close?");
            String accountName = keyboardInput.nextLine();

            if (accountName.equals(currentAccountName)) {
                System.out.println(
                        "You are currently using this account and cannot close it before switching to a new one.");
                System.out.print("Enter the name of the account you'd like to switch to: ");
                String switchToAccount = keyboardInput.nextLine();

                while (!accounts.containsKey(switchToAccount) || switchToAccount.equals(accountName)) {
                    if (switchToAccount.equals(accountName)) {
                        System.out.println(
                                "You cannot switch to the account you are closing. Please enter a different account name.");
                    } else {
                        System.out.println("Account " + switchToAccount + " does not exist. Please try again.");
                    }
                    System.out.print("Enter the name of the account you'd like to switch to: ");
                    switchToAccount = keyboardInput.nextLine();
                }

                this.userAccount = accounts.get(switchToAccount);
                this.currentAccountName = switchToAccount;
                System.out.println("You are now using the " + switchToAccount + " account.");
            }

            accountDeletion.deleteAccount(accountName, currentAccountName, accounts);
            System.out.println("Account " + accountName + " has been closed.");

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