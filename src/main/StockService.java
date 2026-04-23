package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StockService {
    private static final String API_KEY = "fa84dbe1a2914a39bae3198e77a08fa7";

    public double getPrice(String ticker) {
        String json = fetchData(ticker);
        return extractPrice(json);
    }

    private String fetchData(String ticker) {
        try {
            String symbol = ticker.toUpperCase();
            String urlString = "https://api.twelvedata.com/price?symbol="
                    + symbol + "&apikey=" + API_KEY;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            return reader.readLine();
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not retrieve stock data.");
        }
    }

    private double extractPrice(String json) {
        if (json.contains("code")) {
            throw new IllegalArgumentException("Invalid ticker symbol.");
        }

        int start = json.indexOf(":\"") + 2;
        int end = json.indexOf("\"", start);
        return Double.parseDouble(json.substring(start, end));
    }
}