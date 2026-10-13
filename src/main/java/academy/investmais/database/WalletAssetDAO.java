package academy.investmais.database;

import academy.investmais.model.investments.WalletAsset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WalletAssetDAO {

    public void inserir(WalletAsset walletAsset) throws SQLException {
        String sql = """
                INSERT INTO WALLET_ASSET
                    (walletID, assetID, quantity)
                VALUES
                    (?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, walletAsset.getWalletID());
            statement.setString(2, walletAsset.getAssetID());
            statement.setDouble(3, walletAsset.getQuantity());
            statement.executeUpdate();
        }
    }

    public void atualizar(WalletAsset walletAsset) throws SQLException {
        String sql = """
                UPDATE WALLET_ASSET
                   SET quantity = ?
                 WHERE walletID = ?
                   AND assetID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, walletAsset.getQuantity());
            statement.setString(2, walletAsset.getWalletID());
            statement.setString(3, walletAsset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void excluir(String walletID, String assetID) throws SQLException {
        String sql = "DELETE FROM WALLET_ASSET WHERE walletID = ? AND assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, walletID);
            statement.setString(2, assetID);
            statement.executeUpdate();
        }
    }

    public WalletAsset buscarPorId(String walletID, String assetID) throws SQLException {
        String sql = """
                SELECT walletID, assetID, quantity
                  FROM WALLET_ASSET
                 WHERE walletID = ?
                   AND assetID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, walletID);
            statement.setString(2, assetID);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapear(result);
                }
            }
        }

        return null;
    }

    public List<WalletAsset> listarTodos() throws SQLException {
        String sql = """
                SELECT walletID, assetID, quantity
                  FROM WALLET_ASSET
                 ORDER BY walletID, assetID
                """;

        List<WalletAsset> items = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                items.add(mapear(result));
            }
        }

        return items;
    }

    private WalletAsset mapear(ResultSet result) throws SQLException {
        return new WalletAsset(
                result.getString("walletID"),
                result.getString("assetID"),
                result.getDouble("quantity")
        );
    }
}
