package main;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionHistory {

    private List<String> history;

    public ViewTransactionHistory() {
        this.history = new ArrayList<>();
    }

    // Add a new transaction entry
    public void record(String entry) {
        history.add(entry);
    }

    // Return the full history list
    public List<String> viewHistory() {
        return history;
    }
}