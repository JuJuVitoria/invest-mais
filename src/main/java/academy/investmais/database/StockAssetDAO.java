package academy.investmais.database;

import academy.investmais.model.investments.StockAsset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockAssetDAO {

    public void inserir(StockAsset asset) throws SQLException {
        String sql = "INSERT INTO STOCK_ASSET (assetID) VALUES (?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void atualizar(StockAsset asset) throws SQLException {
        String sql = "UPDATE STOCK_ASSET SET assetID = ? WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.setString(2, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void excluir(String assetID) throws SQLException {
        String sql = "DELETE FROM STOCK_ASSET WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, assetID);
            statement.executeUpdate();
        }
    }

    public StockAsset buscarPorId(String assetID) throws SQLException {
        String sql = "SELECT assetID FROM STOCK_ASSET WHERE assetID = ?";

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

    public List<StockAsset> listarTodos() throws SQLException {
        String sql = "SELECT assetID FROM STOCK_ASSET ORDER BY assetID";
        List<StockAsset> assets = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                assets.add(mapear(result));
            }
        }

        return assets;
    }

    private StockAsset mapear(ResultSet result) throws SQLException {
        StockAsset asset = new StockAsset("", "", 0);
        asset.setAssetID(result.getString("assetID"));
        return asset;
    }
}
