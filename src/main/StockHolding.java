package main;

public class StockHolding {
    private String ticker;
    private double shares;
    private double averagePrice;

    public StockHolding(String ticker, double shares, double averagePrice) {
        this.ticker = ticker.toUpperCase();
        this.shares = shares;
        this.averagePrice = averagePrice;
    }

    public String getTicker() {
        return ticker;
    }

    public double getShares() {
        return shares;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void buyMore(double newShares, double newPrice) {
        double totalCost = shares * averagePrice + newShares * newPrice;
        shares += newShares;
        averagePrice = totalCost / shares;
    }

    public String summary() {
        return ticker + ": " + shares + " shares @ $" + averagePrice;
    }
}