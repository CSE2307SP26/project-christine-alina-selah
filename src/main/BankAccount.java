package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
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
