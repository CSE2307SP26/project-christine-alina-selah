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

}
