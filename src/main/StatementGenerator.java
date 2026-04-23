package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatementGenerator {
    
    private BankAccount account;
    private TransactionHistory history;

    public StatementGenerator(BankAccount account, TransactionHistory history) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (history ==null) {
            throw new IllegalArgumentException("Transaction History cannot be null");
        }

        this.account = account;
        this.history = history;
    }

    public String generateStatement() {
        StringBuilder builder = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        builder.append("====================================\n");
        builder.append("BANK STATEMENT\n");
        builder.append("Generated: ").append(LocalDateTime.now().format(formatter)).append("\n");
        builder.append("====================================\n");

        builder.append("Account Name: ").append(account.getAccountName()).append("\n");
        builder.append("Account Type: ").append(account.getAccountType()).append("\n");
        builder.append(String.format("Current Balance: $%.2f%n", account.getBalance()));

        if (account.isJointAccount()) {
            builder.append("Joint Account: Yes\n");
            builder.append("Second Owner: ").append(account.getSecondOwnerName()).append("\n");
        } else {
            builder.append("Joint Account: No\n");
        }

        builder.append("\nTransaction History:\n");

        if (history.viewHistory().isEmpty()) {
            builder.append("No transactions found.\n");
        } else {
            int count = 1;
            for (String entry : history.viewHistory()) {
                builder.append(count).append(". ").append(entry).append("\n");
                count++;
            }
        }

        builder.append("====================================\n");

        return builder.toString();
    }

    private void saveStatementToFile() {
        String fileName = account.getAccountName() + "_statment.txt";

      try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        writer.print(generateStatement());
        System.out.println("Statement saved to " + fileName);
    } catch (IOException e) {
        System.out.println("Error saving statement: " + e.getMessage());
    }
    }
    
}