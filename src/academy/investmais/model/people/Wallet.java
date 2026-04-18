package academy.investmais.model.people;

import java.util.UUID;

public class Wallet {
    private String walletID;
    private String userID;
    private long totalBalance;

    public Wallet(String userID) {
        walletID = UUID.randomUUID().toString();
        this.userID = userID;
    }
}
