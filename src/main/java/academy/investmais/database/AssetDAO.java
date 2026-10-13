package academy.investmais.database;

import academy.investmais.model.investments.Asset;
import academy.investmais.model.investments.CryptoAsset;
import academy.investmais.model.investments.FixedIncomeAsset;
import academy.investmais.model.investments.RealEstateFundsAsset;
import academy.investmais.model.investments.StockAsset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssetDAO {

    public void inserir(Asset asset) throws SQLException {
        String sql = """
                INSERT INTO ASSET
                    (assetID, assetName, assetTicker, currentValue, assetType)
                VALUES
                    (?, ?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.setString(2, asset.getAssetName());
            statement.setString(3, asset.getAssetTicker());
            statement.setDouble(4, asset.getCurrentValue());
            statement.setString(5, tipoDe(asset));
            statement.executeUpdate();
        }
    }

    public void atualizar(Asset asset) throws SQLException {
        String sql = """
                UPDATE ASSET
                   SET assetName = ?,
                       assetTicker = ?,
                       currentValue = ?,
                       assetType = ?
                 WHERE assetID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetName());
            statement.setString(2, asset.getAssetTicker());
            statement.setDouble(3, asset.getCurrentValue());
            statement.setString(4, tipoDe(asset));
            statement.setString(5, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void excluir(String assetID) throws SQLException {
        String sql = "DELETE FROM ASSET WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, assetID);
            statement.executeUpdate();
        }
    }

    public Asset buscarPorId(String assetID) throws SQLException {
        String sql = """
                SELECT assetID, assetName, assetTicker, currentValue, assetType
                  FROM ASSET
                 WHERE assetID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, assetID);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapear(result);
                }
            }
        }

        return null;
    }

    public List<Asset> listarTodos() throws SQLException {
        String sql = """
                SELECT assetID, assetName, assetTicker, currentValue, assetType
                  FROM ASSET
                 ORDER BY assetName
                """;

        List<Asset> assets = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                assets.add(mapear(result));
            }
        }

        return assets;
    }

    private Asset mapear(ResultSet result) throws SQLException {
        String type = result.getString("assetType");
        String name = result.getString("assetName");
        String ticker = result.getString("assetTicker");
        double value = result.getDouble("currentValue");

        Asset asset = switch (type) {
            case "CRYPTO" -> new CryptoAsset(name, ticker, value);
            case "STOCK" -> new StockAsset(name, ticker, value);
            case "FIXED_INCOME" -> new FixedIncomeAsset(name, ticker, value);
            case "REAL_ESTATE" -> new RealEstateFundsAsset(name, ticker, value);
            default -> throw new SQLException("Tipo de ativo invalido: " + type);
        };
        asset.setAssetID(result.getString("assetID"));
        return asset;
    }

    private String tipoDe(Asset asset) throws SQLException {
        if (asset instanceof CryptoAsset) {
            return "CRYPTO";
        }
        if (asset instanceof StockAsset) {
            return "STOCK";
        }
        if (asset instanceof FixedIncomeAsset) {
            return "FIXED_INCOME";
        }
        if (asset instanceof RealEstateFundsAsset) {
            return "REAL_ESTATE";
        }
        throw new SQLException("Tipo de ativo nao mapeado para a tabela ASSET");
    }
}
