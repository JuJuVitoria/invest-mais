package academy.investmais.model.investments;

import java.util.UUID;

public abstract class Asset {
    private String assetID;
    private String assetName;
    private double currentValeu;
    private String assetTicker;

    public Asset(String assetName, String assetTicker, double currentValeu) {
        this.assetID = UUID.randomUUID().toString();
        this.assetName = assetName;
        this.assetTicker = assetTicker;
        this.currentValeu = currentValeu;
    }

    public String getAssetTicker() {
        return assetTicker;
    }

    public double getCurrentValeu() {
        return currentValeu;
    }

    public void setCurrentValeu(double currentValeu) {
        this.currentValeu = currentValeu;
    }

    @Override
    public String toString() {
        return assetName;
    }
}
