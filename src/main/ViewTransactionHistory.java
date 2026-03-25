package main;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionHistory {

    private List<String> history;

    public ViewTransactionHistory() {
        this.history = new ArrayList<>();
    }

    public void record(String entry) {
        history.add(entry);
    }

    public List<String> viewHistory() {
        return history;
    }
}
