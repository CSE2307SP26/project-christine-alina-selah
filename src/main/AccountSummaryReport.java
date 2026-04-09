package main;

public class AccountSummaryReport {

    private BankAccount account;

    public AccountSummaryReport(BankAccount account) {
        this.account = account;
    }

    public String generateSummary() {
        return account.getAccountName() + " (" + account.getAccountType() + "): $" + account.getBalance();
    }
}