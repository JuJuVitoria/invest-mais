package academy.investmais.model.people;

import java.util.Map;
import java.util.UUID;

public class Wallet {
    private String walletID;
    private String userID;
    private long totalBalance;
    private Map<String, Double> assets;

    public Wallet(String userID) {
        walletID = UUID.randomUUID().toString();
        this.userID = userID;
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
}
