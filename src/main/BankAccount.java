package main;

public class BankAccount {

    private double balance;
    private String accountName;
    private String password;


    public BankAccount(String accountName, String password) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty") ;
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.balance = 0;
        this.accountName = accountName;
        this.password = password;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
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

}
