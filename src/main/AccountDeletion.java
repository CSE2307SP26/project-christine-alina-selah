package main;

import java.util.HashMap;

public class AccountDeletion {

    public BankAccount deleteAccount(String accountName, String currentAccountName,
            HashMap<String, BankAccount> accounts) {

        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty.");
        }

        if (!accounts.containsKey(accountName)) {
            throw new IllegalArgumentException("Account " + accountName + " does not exist.");
        }

        if (accounts.size() == 1) {
            throw new IllegalArgumentException(
                    "You cannot close your last account. Please create a new one before trying again.");
        }

        if (accountName.equals(currentAccountName)) {
            throw new IllegalArgumentException(
                    "You are currently using this account and cannot close it before switching to a new one.");
        }

        accounts.remove(accountName);

        return accounts.values().iterator().next();
    }
}