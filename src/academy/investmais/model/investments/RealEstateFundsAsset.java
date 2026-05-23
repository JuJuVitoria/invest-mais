package academy.investmais.model.investments;

public class RealEstateFundsAsset extends Asset {
    public RealEstateFundsAsset(String assetName, String assetTicker, double currentValue) {
        super(assetName, assetTicker, currentValue);
    }

    @Override
    public String getAssetInfo() {
        return "Fundo imobiliário";
    }
}