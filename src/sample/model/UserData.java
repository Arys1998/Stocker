package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Collections;

public class UserData {
    private Stocks ownedSharesList;
    private String name;
    private String surname;
    private double cash;
    private double totalWalletValue;

    public UserData(String name, String surname, double cash, Stocks stocks) {
        this.name = name;
        this.surname = surname;
        this.cash = cash;
        this.ownedSharesList = stocks;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getTotalWalletValue() {
        return ownedSharesList.getStocksTotalValue();
    }

    public StockShare buyStockShare(StockShare stockShare, int amount) {
        double operationValue = stockShare.getActualPrice() * amount;
        StockShare stockShareToBuy = ownedSharesList.getStockShare(stockShare);
        if (operationValue <= getCash()){
            System.out.println("Buying " + stockShareToBuy.getStockName());
            System.out.println("Cash before operation: " + getCash());

            setCash(getCash() - operationValue);
            stockShareToBuy.setNumberOfShares(stockShareToBuy.getNumberOfShares() + amount);
            stockShareToBuy.setBoughtPrice(stockShareToBuy.getActualPrice());

            System.out.println(stockShareToBuy.getStockName() + " bought for " + operationValue);
            System.out.println("Cash after operation: " + getCash());
            System.out.println("Number of shares after operation: " + stockShareToBuy.getNumberOfShares());
        }

        return stockShareToBuy;
    }

    public StockShare sellStockShare(StockShare stockShare, int amount) {
        double operationValue = stockShare.getActualPrice() * amount;
        StockShare stockShareToSell = ownedSharesList.getStockShare(stockShare);
        if (amount <= stockShare.getNumberOfShares()) {
            System.out.println("Selling " + stockShareToSell.getStockName());
            System.out.println("Cash before operation: " + getCash());

            setCash(getCash() + operationValue);
            stockShareToSell.setNumberOfShares(stockShareToSell.getNumberOfShares() - amount);

            System.out.println(stockShareToSell.getStockName() + " sold for " + operationValue);
            System.out.println("Cash after operation: " + getCash());
            System.out.println("Number of shares after operation: " + stockShareToSell.getNumberOfShares());
        }

        return stockShareToSell;
    }
}