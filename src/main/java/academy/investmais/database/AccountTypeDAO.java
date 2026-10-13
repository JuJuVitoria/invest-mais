package academy.investmais.database;

import academy.investmais.model.people.AccountTypeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountTypeDAO {

    public void inserir(AccountTypeRecord type) throws SQLException {
        String sql = "INSERT INTO ACCOUNT_TYPE (code, name, maxUsers) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, type.getCode());
            statement.setString(2, type.getName());
            statement.setInt(3, type.getMaxUsers());
            statement.executeUpdate();
        }
    }

    public void atualizar(AccountTypeRecord type) throws SQLException {
        String sql = "UPDATE ACCOUNT_TYPE SET name = ?, maxUsers = ? WHERE code = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, type.getName());
            statement.setInt(2, type.getMaxUsers());
            statement.setInt(3, type.getCode());
            statement.executeUpdate();
        }
    }

    public void excluir(int code) throws SQLException {
        String sql = "DELETE FROM ACCOUNT_TYPE WHERE code = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, code);
            statement.executeUpdate();
        }
    }

    public AccountTypeRecord buscarPorId(int code) throws SQLException {
        String sql = "SELECT code, name, maxUsers FROM ACCOUNT_TYPE WHERE code = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, code);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapear(result);
                }
            }
        }

        return null;
    }

    public List<AccountTypeRecord> listarTodos() throws SQLException {
        String sql = "SELECT code, name, maxUsers FROM ACCOUNT_TYPE ORDER BY code";
        List<AccountTypeRecord> types = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                types.add(mapear(result));
            }
        }

        return types;
    }

    private AccountTypeRecord mapear(ResultSet result) throws SQLException {
        return new AccountTypeRecord(
                result.getInt("code"),
                result.getString("name"),
                result.getInt("maxUsers")
        );
    }
}