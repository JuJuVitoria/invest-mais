package academy.investmais.model.investments;

public class CryptoAsset extends Asset {
    private String blockchain;

    public CryptoAsset(String assetName, String assetTicker, double currentValue) {
        super(assetName, assetTicker, currentValue);
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    @Override
    public String getAssetInfo() {
        return "Criptoativo na blockchain: " + blockchain;
    }
}
