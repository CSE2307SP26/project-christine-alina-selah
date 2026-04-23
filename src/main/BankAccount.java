package main;

public class BankAccount {

    private double balance;
    private String accountName;
    private String password;
    private String accountType;
    private String secondOwnerName;
    private boolean jointAccount;
    private double loanBalance;
    private int creditScore;


    public BankAccount(String accountName, String password, String accountType, double initialBalance) {
        validateAccountName(accountName);
        validatePassword(password);
        validateAccountType(accountType);
        validateInitialBalance(initialBalance);

        this.balance = initialBalance;
        this.accountName = accountName;
        this.secondOwnerName = null;
        this.password = password;
        this.accountType = accountType;
        this.jointAccount = false;
        this.loanBalance = 0;
        this.creditScore = 650;
    }

    public BankAccount(String accountName, String secondOwnerName, String password,
            String accountType, double initialBalance) {
        validateAccountName(accountName);
        validateSecondOwnerName(secondOwnerName);
        validatePassword(password);
        validateAccountType(accountType);
        validateInitialBalance(initialBalance);

        this.balance = initialBalance;
        this.accountName = accountName;
        this.secondOwnerName = secondOwnerName;
        this.password = password;
        this.accountType = accountType;
        this.jointAccount = true;
    }

    private void validateAccountName(String accountName) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty");
        }
    }

    private void validateSecondOwnerName(String secondOwnerName) {
        if (secondOwnerName == null || secondOwnerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Second owner name cannot be empty");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

    private void validateAccountType(String accountType) {
        if (accountType == null || accountType.trim().isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be empty");
        }
        if (!accountType.equalsIgnoreCase("checking") && !accountType.equalsIgnoreCase("savings")) {
            throw new IllegalArgumentException("Account type must be savings or checking");
        }
    }

    private void validateInitialBalance(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
    }

    public String getSecondOwnerName() {
        return secondOwnerName;
    }

    public boolean isJointAccount() {
        return jointAccount;
    }

    private boolean isSuspicious(double amount) {
        return amount > balance * 0.5; // suspicious if > 50% of current balance
    }

    public String getSummary() {
        return accountName + " (" + accountType + "): $" + balance;
    }

    public void deposit(double amount) {
        if (amount >= 0) {
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

    public String getAccountType() {
        return accountType;
    }

    public void withdrawal(BankAccount account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough balance.");
        }

        if (isSuspicious(amount)) {
            System.out.println("⚠️ Fraud Alert: This withdrawal is unusually high.");
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
        if (accountType.equalsIgnoreCase("savings")) {
            balance += balance * 0.05;
        } else {
            throw new IllegalArgumentException("Intrest can only be apply to savings account");
        }
    }

    public double getLoanBalance() {
        return loanBalance;
    }
    
    public int getCreditScore() {
        return creditScore;
    }

    public void applyForLoan(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than 0.");
        }
    
        if (creditScore < 600) {
            throw new IllegalArgumentException("Loan denied due to low credit score.");
        }
    
        if (amount > 10000) {
            throw new IllegalArgumentException("Loan amount exceeds maximum allowed.");
        }
    
        loanBalance += amount;
        balance += amount;
    }
    
    public void makeLoanPayment(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than 0.");
        }
    
        if (balance < amount) {
            decreaseCreditScore(15);
            throw new IllegalArgumentException("Not enough balance to make loan payment.");
        }
    
        if (loanBalance <= 0) {
            throw new IllegalArgumentException("No outstanding loan balance.");
        }
    
        if (amount > loanBalance) {
            amount = loanBalance;
        }
    
        balance -= amount;
        loanBalance -= amount;
        increaseCreditScore(10);
    }

    public void increaseCreditScore(int points) {
        creditScore += points;
        if (creditScore > 850) {
            creditScore = 850;
        }
    }
    
    public void decreaseCreditScore(int points) {
        creditScore -= points;
        if (creditScore < 300) {
            creditScore = 300;
        }
    }


}
