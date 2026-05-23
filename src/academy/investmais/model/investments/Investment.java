package academy.investmais.model.investments;

import java.util.UUID;

public class Investment {
    private String investmentID;
    private String walletID;
    private String assetID;
    private double quantity;
    private double purchasePrice;

    public Investment(String walletID, String assetID, double quantity, double purchasePrice) {
        this.investmentID = UUID.randomUUID().toString();
        this.walletID = walletID;
        this.assetID = assetID;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public String getInvestmentID() {
        return investmentID;
    }

    public void setInvestmentID(String investmentID) {
        this.investmentID = investmentID;
    }

    public String getWalletID() {
        return walletID;
    }

    public void setWalletID(String walletID) {
        this.walletID = walletID;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}