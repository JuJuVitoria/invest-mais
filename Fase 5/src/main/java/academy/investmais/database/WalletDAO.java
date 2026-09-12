package academy.investmais.database;

import academy.investmais.model.people.Wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WalletDAO {

    public void inserir(Wallet wallet) throws SQLException {
        String sql = """
                INSERT INTO WALLET
                    (walletID, CategoryID, userID, name, totalBalance)
                VALUES
                    (?, ?, ?, ?, ?)
                """;

        try (Connection connection = academy.investmais.database.ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, wallet.getWalletID());
            statement.setInt(2, wallet.getCategoryID());
            statement.setString(3, wallet.getUserID());
            statement.setString(4, wallet.getName());
            statement.setLong(5, wallet.getBalance());

            statement.executeUpdate();
        }
    }

    public void atualizar(Wallet wallet) throws SQLException {
        String sql = """
                UPDATE WALLET
                   SET CategoryID = ?,
                       userID = ?,
                       name = ?,
                       totalBalance = ?
                 WHERE walletID = ?
                """;

        try (Connection connection = academy.investmais.database.ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, wallet.getCategoryID());
            statement.setString(2, wallet.getUserID());
            statement.setString(3, wallet.getName());
            statement.setLong(4, wallet.getBalance());
            statement.setString(5, wallet.getWalletID());

            statement.executeUpdate();
        }
    }

    public void excluir(String walletID) throws SQLException {
        String sql = "DELETE FROM WALLET WHERE walletID = ?";

        try (Connection connection = academy.investmais.database.ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, walletID);
            statement.executeUpdate();
        }
    }

    public Wallet buscarPorId(String walletID) throws SQLException {
        String sql = """
                SELECT walletID, CategoryID, userID, name, totalBalance
                  FROM WALLET
                 WHERE walletID = ?
                """;

        try (Connection connection = academy.investmais.database.ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, walletID);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapear(result);
                }
            }
        }

        return null;
    }

    public List<Wallet> listarTodos() throws SQLException {
        String sql = """
                SELECT walletID, CategoryID, userID, name, totalBalance
                  FROM WALLET
                 ORDER BY name
                """;

        List<Wallet> wallets = new ArrayList<>();

        try (Connection connection = academy.investmais.database.ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                wallets.add(mapear(result));
            }
        }

        return wallets;
    }

    private Wallet mapear(ResultSet result) throws SQLException {
        Wallet wallet = new Wallet(
                result.getString("userID"),
                result.getLong("totalBalance"),
                result.getString("name"),
                result.getInt("CategoryID")
        );
        wallet.setWalletID(result.getString("walletID"));
        return wallet;
    }
}
