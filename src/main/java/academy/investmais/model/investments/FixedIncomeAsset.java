package academy.investmais.model.investments;

public class FixedIncomeAsset extends Asset {
    public FixedIncomeAsset(String assetName, String assetTicker, double currentValue) {
        super(assetName, assetTicker, currentValue);
    }

    @Override
    public String getAssetInfo() {
        return "Ativo de renda fixa";
    }
}