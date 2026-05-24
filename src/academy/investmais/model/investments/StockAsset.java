package academy.investmais.model.investments;

public class StockAsset extends Asset {
    public StockAsset(String assetName, String assetTicker, double currentValue) {
        super(assetName, assetTicker, currentValue);
    }

    @Override
    public String getAssetInfo() {
        return "Ativo de renda variável (ação)";
    }
}