package academy.investmais.model.investments;

import java.util.UUID;

public abstract class Asset {
    private String assetID;
    private String assetName;
    private double currentValue;
    private String assetTicker;

    public Asset(String assetName, String assetTicker, double currentValue) {
        this.assetID = UUID.randomUUID().toString();
        this.assetName = assetName;
        this.assetTicker = assetTicker;
        this.currentValue = currentValue;
    }
    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setAssetTicker(String assetTicker) {
        this.assetTicker = assetTicker;
    }

    public String getAssetTicker() {
        return assetTicker;
    }

    public double getCurrentValeu() {
        return currentValue;
    }

    public void setCurrentValeu(double currentValeu) {
        this.currentValue = currentValeu;
    }

    public String getAssetInfo() {
        return "Ativo: " + assetName;
    }

    @Override
    public String toString() {
        return assetName;
    }
}