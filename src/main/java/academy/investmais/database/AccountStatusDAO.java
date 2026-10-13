package academy.investmais.database;

import academy.investmais.model.people.AccountStatusRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountStatusDAO {

    public void inserir(AccountStatusRecord status) throws SQLException {
        String sql = "INSERT INTO ACCOUNT_STATUS (code, name) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, status.getCode());
            statement.setString(2, status.getName());
            statement.executeUpdate();
        }
    }

    public void atualizar(AccountStatusRecord status) throws SQLException {
        String sql = "UPDATE ACCOUNT_STATUS SET name = ? WHERE code = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status.getName());
            statement.setInt(2, status.getCode());
            statement.executeUpdate();
        }
    }

    public void excluir(int code) throws SQLException {
        String sql = "DELETE FROM ACCOUNT_STATUS WHERE code = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, code);
            statement.executeUpdate();
        }
    }

    public AccountStatusRecord buscarPorId(int code) throws SQLException {
        String sql = "SELECT code, name FROM ACCOUNT_STATUS WHERE code = ?";

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

    public List<AccountStatusRecord> listarTodos() throws SQLException {
        String sql = "SELECT code, name FROM ACCOUNT_STATUS ORDER BY code";
        List<AccountStatusRecord> statuses = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                statuses.add(mapear(result));
            }
        }

        return statuses;
    }

    private AccountStatusRecord mapear(ResultSet result) throws SQLException {
        return new AccountStatusRecord(
                result.getInt("code"),
                result.getString("name")
        );
    }
}