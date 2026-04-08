package main;

import java.util.ArrayList;
import java.util.List;

public class SearchTransactionHistory {

    private ViewTransactionHistory history;

    public SearchTransactionHistory(ViewTransactionHistory history) {
        this.history = history;
    }

    /**
     * Returns all transaction entries that contain the given keyword
     * (case-insensitive). :D
     */
    public List<String> search(String keyword) {
        List<String> results = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }

        String lowerKeyword = keyword.toLowerCase();

        for (String entry : history.viewHistory()) {
            if (entry.toLowerCase().contains(lowerKeyword)) {
                results.add(entry);
            }
        }

        return results;
    }
}