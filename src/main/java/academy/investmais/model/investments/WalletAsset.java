package academy.investmais.model.investments;

public class WalletAsset {
    private String walletID;
    private String assetID;
    private double quantity;

    public WalletAsset(String walletID, String assetID, double quantity) {
        this.walletID = walletID;
        this.assetID = assetID;
        this.quantity = quantity;
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
}
