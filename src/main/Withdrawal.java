package main;

public class Withdrawal {

    public static void withdrawlMoney(BankAccount account, double amount ) {
        if(amount <=0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if(account.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough balance.");
        }

        account.decreaseBalance(amount);

        
    }
    
}
