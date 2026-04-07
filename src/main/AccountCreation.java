package main;

import java.util.HashMap;

public class AccountCreation {

    public BankAccount createAccount(String accountName, double initialBalance,
            HashMap<String, BankAccount> accounts) {

        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty.");
        }

        if (accounts.containsKey(accountName)) {
            throw new IllegalArgumentException("Account name already exists.");
        }

        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        BankAccount newAccount = new BankAccount();
        newAccount.deposit(initialBalance);
        accounts.put(accountName, newAccount);

        return newAccount;
    }
}