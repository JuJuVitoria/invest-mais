package academy.investmais.model.people;

import java.util.Map;
import java.util.UUID;

public class Wallet {
    private String walletID;
    private String userID;
    private String name;
    private long totalBalance;
    private Map<String, Double> assets;

    public Wallet(String userID) {
        walletID = UUID.randomUUID().toString();
        this.userID = userID;
    }

    public Wallet(String userID, long totalBalance) {
        walletID = UUID.randomUUID().toString();

        this.userID = userID;
        this.totalBalance = totalBalance;
    }

    public Wallet(String userID, long totalBalance, String name) {
        walletID = UUID.randomUUID().toString();

        this.userID = userID;
        this.totalBalance = totalBalance;
        this.name = name;
    }

    public void addAsset(String ticker, double quantity) {
        this.assets.put(ticker, quantity);
    }

    public String getWalletID() {
        return walletID;
    }

    public String getUserID() {
        return userID;
    }

    public long getBalance() {
        return this.totalBalance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
