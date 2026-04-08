package main;

public class BankAccount {

    private double balance;
    private String accountName;
    private String password;
    private String accountType;


    public BankAccount(String accountName, String password, String accountType, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be 0");
        } 
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (accountType == null || accountType.trim().isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be empty");
        }
        if (!accountType.equalsIgnoreCase("checking") && !accountType.equalsIgnoreCase("savings")) {
            throw new IllegalArgumentException("Account type must be savings or checkings");
        }
        this.balance = initialBalance;
        this.accountName = accountName;
        this.password = password;
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        if (amount >= 0) {
            this.balance += amount;
        } 
        else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public boolean checkPassword(String userInputPassword) {
        return password.equals(userInputPassword);
    }

    public String getAccountType() {
        return accountType;
    }

    public void withdrawal(BankAccount account, double amount) {
        if(amount <=0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if(account.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough balance.");
        }

        account.balance -= amount;
    }

    public void transfer(BankAccount fromAccount, BankAccount destinationAccount, double amount) {
        if (destinationAccount == null || fromAccount == null) {
            throw new IllegalArgumentException("Bank account does not exist.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if (fromAccount == destinationAccount) {
            throw new IllegalArgumentException("Transfering from the same account");
        }
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough balance to perform transfer.");
        }

        fromAccount.balance -= amount;
        destinationAccount.balance += amount;
    }

    public void applyIntrest() {
        if(accountType.equalsIgnoreCase("savings")){
            balance += balance * 0.05;
        } else {
            throw new IllegalArgumentException("Intrest can only be apply to savings account");
        }
    }

}
