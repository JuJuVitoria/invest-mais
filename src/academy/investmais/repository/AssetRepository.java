package academy.investmais.repository;

import academy.investmais.model.investments.Asset;
import academy.investmais.model.investments.CryptoAsset;
import academy.investmais.model.investments.RealEstateFundsAsset;
import academy.investmais.model.investments.StockAsset;

import java.util.ArrayList;
import java.util.List;

public class AssetRepository {
    private List<Asset> avalibleAssets = new ArrayList<>();

    public AssetRepository() {
        avalibleAssets.add(new CryptoAsset("Bitcoin", "BTC", 75949.69));
        avalibleAssets.add(new CryptoAsset("Ethereum", "ETH", 2360.54));
        avalibleAssets.add(new StockAsset("Vale", "VALE3", 89.75));
        avalibleAssets.add(new RealEstateFundsAsset("VGRI11", "VGRI11", 6.74));
    }

    public List<Asset> getAvalibleAssets() {
        return avalibleAssets;
    }
}
