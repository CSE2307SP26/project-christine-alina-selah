package main;

// import main.BankAccount;

public class Transfer{

    public static void transferService(BankAccount fromAccount, BankAccount destinationAccount, double amount) {
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

        fromAccount.decreaseBalance(amount);
        destinationAccount.increaseBalance(amount);
    
    }
}
