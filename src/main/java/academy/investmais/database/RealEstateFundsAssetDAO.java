package academy.investmais.database;

import academy.investmais.model.investments.RealEstateFundsAsset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RealEstateFundsAssetDAO {

    public void inserir(RealEstateFundsAsset asset) throws SQLException {
        String sql = "INSERT INTO REAL_ESTATE_FUNDS_ASSET (assetID) VALUES (?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void atualizar(RealEstateFundsAsset asset) throws SQLException {
        String sql = "UPDATE REAL_ESTATE_FUNDS_ASSET SET assetID = ? WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.setString(2, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void excluir(String assetID) throws SQLException {
        String sql = "DELETE FROM REAL_ESTATE_FUNDS_ASSET WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, assetID);
            statement.executeUpdate();
        }
    }

    public RealEstateFundsAsset buscarPorId(String assetID) throws SQLException {
        String sql = "SELECT assetID FROM REAL_ESTATE_FUNDS_ASSET WHERE assetID = ?";

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

    public List<RealEstateFundsAsset> listarTodos() throws SQLException {
        String sql = "SELECT assetID FROM REAL_ESTATE_FUNDS_ASSET ORDER BY assetID";
        List<RealEstateFundsAsset> assets = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                assets.add(mapear(result));
            }
        }

        return assets;
    }

    private RealEstateFundsAsset mapear(ResultSet result) throws SQLException {
        RealEstateFundsAsset asset = new RealEstateFundsAsset("", "", 0);
        asset.setAssetID(result.getString("assetID"));
        return asset;
    }
}
