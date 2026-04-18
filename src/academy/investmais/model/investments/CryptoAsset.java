package academy.investmais.model.investments;

public class CryptoAsset extends Asset {
    private String blockchain;

    public CryptoAsset(String assetName, String assetTicker, double currentValeu) {
        super(assetName, assetTicker, currentValeu);
    }
}
