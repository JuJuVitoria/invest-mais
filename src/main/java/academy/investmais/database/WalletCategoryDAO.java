package academy.investmais.database;
import academy.investmais.model.people.WalletCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class WalletCategoryDAO {
    public void inserir(WalletCategory category) throws SQLException {
        String sql = "INSERT INTO WALLET_CATEGORY (categoryID, name) VALUES (?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, category.getCategoryID());
            statement.setString(2, category.getName());
            statement.executeUpdate();
        }
    }
    public void atualizar(WalletCategory category) throws SQLException {
        String sql = "UPDATE WALLET_CATEGORY SET name = ? WHERE categoryID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getCategoryID());
            statement.executeUpdate();
        }
    }
    public void excluir(int categoryID) throws SQLException {
        String sql = "DELETE FROM WALLET_CATEGORY WHERE categoryID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryID);
            statement.executeUpdate();
        }
    }
    public WalletCategory buscarPorId(int categoryID) throws SQLException {
        String sql = "SELECT categoryID, name FROM WALLET_CATEGORY WHERE categoryID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryID);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapear(result);
                }
            }
        }
        return null;
    }
    public List<WalletCategory> listarTodos() throws SQLException {
        String sql = "SELECT categoryID, name FROM WALLET_CATEGORY ORDER BY categoryID";
        List<WalletCategory> categories = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                categories.add(mapear(result));
            }
        }
        return categories;
    }
    private WalletCategory mapear(ResultSet result) throws SQLException {
        return new WalletCategory(
                result.getInt("categoryID"),
                result.getString("name")
        );
    }
}