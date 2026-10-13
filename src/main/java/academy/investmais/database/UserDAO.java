package academy.investmais.database;

import academy.investmais.model.people.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void inserir(User user) throws SQLException {
        String sql = """
                INSERT INTO "USER"
                    (userID, accountID, name, email, password)
                VALUES
                    (?, ?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUserID());
            statement.setString(2, user.getAccountID());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.executeUpdate();
        }
    }

    public void atualizar(User user) throws SQLException {
        String sql = """
                UPDATE "USER"
                   SET accountID = ?,
                       name = ?,
                       email = ?,
                       password = ?
                 WHERE userID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getAccountID());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUserID());
            statement.executeUpdate();
        }
    }

    public void excluir(String userID) throws SQLException {
        String sql = "DELETE FROM \"USER\" WHERE userID = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userID);
            statement.executeUpdate();
        }
    }

    public User buscarPorId(String userID) throws SQLException {
        String sql = """
                SELECT userID, accountID, name, email, password
                  FROM "USER"
                 WHERE userID = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userID);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapear(result);
                }
            }
        }

        return null;
    }

    public List<User> listarTodos() throws SQLException {
        String sql = """
                SELECT userID, accountID, name, email, password
                  FROM "USER"
                 ORDER BY name
                """;

        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                users.add(mapear(result));
            }
        }

        return users;
    }

    private User mapear(ResultSet result) throws SQLException {
        User user = new User(
                result.getString("name"),
                result.getString("email"),
                result.getString("password"),
                result.getString("accountID")
        );
        user.setUserID(result.getString("userID"));
        return user;
    }
}
