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
// Shook said be axed
    // public void increaseBalance(double amount) {
    //     this.balance += amount;
    // }

    // public void decreaseBalance(double amount) {
    //     this.balance -= amount;
    // }

}
