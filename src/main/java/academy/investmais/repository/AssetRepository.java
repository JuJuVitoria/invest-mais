package academy.investmais.repository;

import academy.investmais.model.investments.Asset;
import academy.investmais.model.investments.CryptoAsset;
import academy.investmais.model.investments.RealEstateFundsAsset;
import academy.investmais.model.investments.StockAsset;

import java.util.ArrayList;
import java.util.List;

public class AssetRepository {
    private List<Asset> availableAssets = new ArrayList<>();

    public AssetRepository() {
        availableAssets.add(new CryptoAsset("Bitcoin", "BTC", 75949.69));
        availableAssets.add(new CryptoAsset("Ethereum", "ETH", 2360.54));
        availableAssets.add(new StockAsset("Vale", "VALE3", 89.75));
        availableAssets.add(new RealEstateFundsAsset("VGRI11", "VGRI11", 6.74));
    }

    public List<Asset> getAvailableAssets() {
        return availableAssets;
    }

    public Asset findAssetByTicker(String ticker) {
        return availableAssets.stream()
                .filter(asset -> asset.getAssetTicker().equalsIgnoreCase(ticker))
                .findFirst()
                .orElse(null);
    }
}
