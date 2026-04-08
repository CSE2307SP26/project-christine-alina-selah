package main;

import java.util.HashMap;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 10;
    private static final int MAX_SELECTION = 10;

    private BankAccount userAccount;
    private BankAccount secondAccount;
    private Scanner keyboardInput;
    private ViewTransactionHistory history;

    // Adding hashmap in order to keep track of multiple bank accounts
    private HashMap<String, BankAccount> accounts;
    private String currentAccountName;

    public MainMenu() {
        this.userAccount = new BankAccount("default", "1234", "checking", 0);
        this.secondAccount = new BankAccount("second", "1234", "checking", 0);
        this.keyboardInput = new Scanner(System.in);

        // Constructing hashmap, setting default bank account, and adding said bank
        // account to hashmap
        this.accounts = new HashMap<>();
        this.currentAccountName = "default";
        this.accounts.put(this.currentAccountName, this.userAccount);
        this.accounts.put("second", this.secondAccount);
        this.history = new ViewTransactionHistory();

    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        System.out.println("1. Login");
        System.out.println("2. Make a deposit");
        System.out.println("3. Make a withdrawal");
        System.out.println("4. Make a transfer");
        System.out.println("5. Create a new account");
        System.out.println("6. Close an existing account");
        System.out.println("7. Apply interest (savings account only)");
        System.out.println("8. View transaction history");
        System.out.println("9. Check account balance"); // <-- ADD THIS
        System.out.println("10. Exit the app");
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
                performLogin();
                break;
            case 2:
                performDeposit();
                break;

            case 3:
                performWithdrawal();
                break;

            case 4:
                performTransfer();
                break;

            case 5:
                performCreateAccount();
                break;

            case 6:
                performCloseAccount();
                break;
            case 7:
                performApplyIntrest();
                break;
            case 8:
                performCheckBalance();
                break;
            case 9:
                System.out.println("Goodbye");
                break;

            default:
                break;
        }
    }

    public void displayHistory() {
        System.out.println("Transaction History:");
        for (String entry : history.viewHistory()) {
            System.out.println(entry);
        }
    }

    public void performLogin() {
        System.out.println("Enter account name:");
        String accountName = keyboardInput.nextLine().trim();

        if (!accounts.containsKey(accountName)) {
            System.out.println("Account does not exist.");
            return;
        }

        System.out.println("Enter Password:");
        String password = keyboardInput.nextLine();

        BankAccount account = accounts.get(accountName);

        if (account.checkPassword(password)) {
            userAccount = account;
            currentAccountName = accountName;
            System.out.println("Login successful. Your are using " + accountName);

        } else {
            System.out.println("incorrect password");
        }
    }

    public void performApplyIntrest() {
        try {
            userAccount.applyIntrest();
            System.out.println("Intrest applied, New balance: $" + userAccount.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to create a new account
    public void performCreateAccount() {
        try {
            System.out.println("What would you like to name your new account?");
            String accountName = keyboardInput.nextLine().trim();

            System.out.println("What would the password to the account be?");
            String password = keyboardInput.nextLine().trim();

            System.out.println("What type of account would it be? (savings/checking)");
            String accountType = keyboardInput.nextLine().trim();

            System.out.print("How much would you like to deposit: ");
            double initialBalance = keyboardInput.nextDouble();
            keyboardInput.nextLine();

            createAccount(accountName, password, accountType, initialBalance);
            System.out.println("You are now using the " + accountName
                    + " account with a balance of $" + userAccount.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createAccount(String accountName, String password, String accountType, double initialBalance) {
        // if (accountName.isEmpty()) {
        // throw new IllegalArgumentException("Account name cannot be empty.");
        // }
        // if (accounts.containsKey(accountName)) {
        // throw new IllegalArgumentException("Account name already exists.");
        // }
        // if (initialBalance < 0) {
        // throw new IllegalArgumentException("Initial balance cannot be negative.");
        // }

        BankAccount newAccount = new BankAccount(accountName, password, accountType, initialBalance);
        newAccount.deposit(initialBalance);
        accounts.put(accountName, newAccount);
        userAccount = newAccount;
        currentAccountName = accountName;
    }

    // Method to close an account
    public void performCloseAccount() {
        try {
            System.out.println("Which account would you like to close?");
            String accountName = keyboardInput.nextLine().trim();

            if (accountName.isEmpty()) {
                throw new IllegalArgumentException("Account name cannot be empty.");
            }
            if (accountName.equals(currentAccountName)) {
                String switchToAccount = promptForSwitchAccount(accountName);
                userAccount = accounts.get(switchToAccount);
                currentAccountName = switchToAccount;
                System.out.println("You are now using the " + switchToAccount + " account.");
            }

            closeAccount(accountName);
            System.out.println("Account " + accountName + " has been closed.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeAccount(String accountName) {
        if (!accounts.containsKey(accountName)) {
            throw new IllegalArgumentException("Account " + accountName + " does not exist.");
        }
        if (accounts.size() == 1) {
            throw new IllegalArgumentException(
                    "You cannot close your last account. Please create a new one before trying again.");
        }

        accounts.remove(accountName);
    }

    public String promptForSwitchAccount(String accountName) {
        System.out.println("You are currently using this account and cannot close it before switching to a new one.");

        while (true) {
            System.out.print("Enter the name of the account you'd like to switch to: ");
            String switchToAccount = keyboardInput.nextLine().trim();

            if (switchToAccount.isEmpty()) {
                System.out.println("Account name cannot be empty.");
            } else if (switchToAccount.equals(accountName)) {
                System.out.println("You cannot switch to the account you are closing.");
            } else if (!accounts.containsKey(switchToAccount)) {
                System.out.println("Account " + switchToAccount + " does not exist.");
            } else {
                return switchToAccount;
            }
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
        }
        userAccount.deposit(depositAmount);
        history.record("Deposited $" + depositAmount);
    }

    public void performTransfer() {
        double transferAmount = -1;
        while (transferAmount < 0) {
            System.out.println("How much money would you like transfer: ");
            transferAmount = keyboardInput.nextInt();

        }
        userAccount.transfer(userAccount, secondAccount, transferAmount);
    }

    public void performWithdrawal() {
        double withdrawlAmount = -1;

        while (withdrawlAmount < 0) {
            System.out.println("How much would you like to withdrawl: ");
            withdrawlAmount = keyboardInput.nextDouble();
        }

        userAccount.withdrawal(userAccount, withdrawlAmount);
    }

    public void performCheckBalance() {
        CheckBankAccount checker = new CheckBankAccount(userAccount.getBalance());
        double balance = checker.checkBalance();
        System.out.println("Your current balance is: $" + balance);
    }

    public void run() {
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public HashMap<String, BankAccount> getAccounts() {
        return accounts;
    }

    public String getCurrentAccountName() {
        return currentAccountName;
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
 

