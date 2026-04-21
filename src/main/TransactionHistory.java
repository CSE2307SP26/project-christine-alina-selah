package main;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {

    private List<String> history;

    public TransactionHistory() {
        this.history = new ArrayList<>();
    }

    public void record(String entry) {
        history.add(entry);
    }

    public List<String> viewHistory() {
        return history;
    }

    public List<String> search(String keyword) {
        List<String> results = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }

        String lower = keyword.toLowerCase();

        for (String entry : history) {
            if (entry.toLowerCase().contains(lower)) {
                results.add(entry);
            }
        }

        return results;
    }
}
