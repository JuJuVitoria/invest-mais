package academy.investmais.database;

import academy.investmais.model.investments.CryptoAsset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CryptoAssetDAO {

    public void inserir(CryptoAsset asset) throws SQLException {
        String sql = "INSERT INTO CRYPTO_ASSET (assetID, blockchain) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.setString(2, asset.getBlockchain());
            statement.executeUpdate();
        }
    }

    public void atualizar(CryptoAsset asset) throws SQLException {
        String sql = "UPDATE CRYPTO_ASSET SET blockchain = ? WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getBlockchain());
            statement.setString(2, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void excluir(String assetID) throws SQLException {
        String sql = "DELETE FROM CRYPTO_ASSET WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, assetID);
            statement.executeUpdate();
        }
    }

    public CryptoAsset buscarPorId(String assetID) throws SQLException {
        String sql = "SELECT assetID, blockchain FROM CRYPTO_ASSET WHERE assetID = ?";

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

    public List<CryptoAsset> listarTodos() throws SQLException {
        String sql = "SELECT assetID, blockchain FROM CRYPTO_ASSET ORDER BY assetID";
        List<CryptoAsset> assets = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                assets.add(mapear(result));
            }
        }

        return assets;
    }

    private CryptoAsset mapear(ResultSet result) throws SQLException {
        CryptoAsset asset = new CryptoAsset("", "", 0);
        asset.setAssetID(result.getString("assetID"));
        asset.setBlockchain(result.getString("blockchain"));
        return asset;
    }
}
