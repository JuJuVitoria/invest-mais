package academy.investmais.database;

import academy.investmais.model.investments.FixedIncomeAsset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FixedIncomeAssetDAO {

    public void inserir(FixedIncomeAsset asset) throws SQLException {
        String sql = "INSERT INTO FIXED_INCOME_ASSET (assetID) VALUES (?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void atualizar(FixedIncomeAsset asset) throws SQLException {
        String sql = "UPDATE FIXED_INCOME_ASSET SET assetID = ? WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, asset.getAssetID());
            statement.setString(2, asset.getAssetID());
            statement.executeUpdate();
        }
    }

    public void excluir(String assetID) throws SQLException {
        String sql = "DELETE FROM FIXED_INCOME_ASSET WHERE assetID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, assetID);
            statement.executeUpdate();
        }
    }

    public FixedIncomeAsset buscarPorId(String assetID) throws SQLException {
        String sql = "SELECT assetID FROM FIXED_INCOME_ASSET WHERE assetID = ?";

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

    public List<FixedIncomeAsset> listarTodos() throws SQLException {
        String sql = "SELECT assetID FROM FIXED_INCOME_ASSET ORDER BY assetID";
        List<FixedIncomeAsset> assets = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                assets.add(mapear(result));
            }
        }

        return assets;
    }

    private FixedIncomeAsset mapear(ResultSet result) throws SQLException {
        FixedIncomeAsset asset = new FixedIncomeAsset("", "", 0);
        asset.setAssetID(result.getString("assetID"));
        return asset;
    }
}
