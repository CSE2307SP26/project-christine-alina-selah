package main;

import java.util.HashMap;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 6;
    private static final int MAX_SELECTION = 6;

    private BankAccount userAccount;
    private BankAccount secondAccount;
    private Scanner keyboardInput;

    // Adding hashmap in order to keep track of multiple bank accounts
    private HashMap<String, BankAccount> accounts;
    private String currentAccountName;
    private AccountDeletion accountDeletion;
    private AccountCreation accountCreation;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.secondAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);

        // Constructing hashmap, setting default bank account, and adding said bank
        // account to hashmap
        this.accounts = new HashMap<>();
        this.currentAccountName = "default";
        this.accounts.put(this.currentAccountName, this.userAccount);

        this.accountDeletion = new AccountDeletion();
        this.accountCreation = new AccountCreation();
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdrawal");
        System.out.println("3. Make a transfer");
        System.out.println("4. Create a new account");
        System.out.println("5. Close an existing account");
        System.out.println("6. Exit the app");
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
      
            case 2:
                performWithdrawal();
                break;
            
            case 3:
                performTransfer();
                break;
            
            case 4:
                performCreateAccount();
            
            case 5:
                performCloseAccount(); 
            
            case 6:
                System.out.println("Goodbye");
                break;

            default:
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
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
    public void performTransfer() {
        double transferAmount = -1;
        while (transferAmount < 0) {
            System.out.println("How much money would you like transfer: ");
            transferAmount = keyboardInput.nextInt();

        }
        Transfer.transferService(userAccount, secondAccount, transferAmount);
    }

    public void performWithdrawal() {
        double withdrawlAmount = -1;

        while (withdrawlAmount < 0) {
            System.out.println("How much would you like to withdrawl: ");
            withdrawlAmount = keyboardInput.nextDouble();
        }

        Withdrawal.withdrawlMoney(userAccount, withdrawlAmount);
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