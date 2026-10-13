package academy.investmais.database;

import academy.investmais.enums.AccountStatus;
import academy.investmais.enums.AccountType;
import academy.investmais.model.people.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void inserir(Account account) throws SQLException {
        String sql = """
                INSERT INTO ACCOUNT
                    (accountID, idPrimaryOwner, accountTypeCode, accountStatusCode)
                VALUES
                    (?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, account.getAccountID());
            statement.setString(2, account.getPrimaryOwnerID());
            statement.setInt(3, account.getAccountType().getCode());
            statement.setInt(4, account.getAccountStatus().getCode());
            statement.executeUpdate();
        }
    }

    public void atualizar(Account account) throws SQLException {
        String sql = """
                UPDATE ACCOUNT
                   SET idPrimaryOwner = ?,
                       accountTypeCode = ?,
                       accountStatusCode = ?
                 WHERE accountID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, account.getPrimaryOwnerID());
            statement.setInt(2, account.getAccountType().getCode());
            statement.setInt(3, account.getAccountStatus().getCode());
            statement.setString(4, account.getAccountID());
            statement.executeUpdate();
        }
    }

    public void excluir(String accountID) throws SQLException {
        String sql = "DELETE FROM ACCOUNT WHERE accountID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountID);
            statement.executeUpdate();
        }
    }

    public Account buscarPorId(String accountID) throws SQLException {
        String sql = """
                SELECT accountID, idPrimaryOwner, accountTypeCode, accountStatusCode
                  FROM ACCOUNT
                 WHERE accountID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountID);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapear(result);
                }
            }
        }

        return null;
    }

    public List<Account> listarTodos() throws SQLException {
        String sql = """
                SELECT accountID, idPrimaryOwner, accountTypeCode, accountStatusCode
                  FROM ACCOUNT
                 ORDER BY accountID
                """;

        List<Account> accounts = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                accounts.add(mapear(result));
            }
        }

        return accounts;
    }

    private Account mapear(ResultSet result) throws SQLException {
        AccountType type = tipoPorCodigo(result.getInt("accountTypeCode"));
        AccountStatus status = statusPorCodigo(result.getInt("accountStatusCode"));

        Account account = new Account(type);
        account.setAccountID(result.getString("accountID"));
        account.setPrimaryOwnerID(result.getString("idPrimaryOwner"));
        account.setAccountStatus(status);
        return account;
    }

    private AccountType tipoPorCodigo(int code) {
        for (AccountType type : AccountType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return AccountType.PERSONAL;
    }

    private AccountStatus statusPorCodigo(int code) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return AccountStatus.ACTIVE;
    }
}