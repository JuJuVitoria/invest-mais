package academy.investmais.model.people;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Wallet {
    private String walletID;
    private String userID;
    private String name;
    private long totalBalance;
    private Map<String, Double> assets = new HashMap<>();

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
    public void setWalletID(String walletID) {
        this.walletID = walletID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setBalance(long totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Map<String, Double> getAssets() {
        return assets;
    }

    public void setAssets(Map<String, Double> assets) {
        this.assets = assets;
    }

    public String getName() { return name; }
}
