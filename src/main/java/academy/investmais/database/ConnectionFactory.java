package academy.investmais.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {
    private static final String URL =
            System.getenv().getOrDefault("ORACLE_URL", "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL");
    private static final String USER =
            System.getenv().getOrDefault("ORACLE_USER", "rm562439");
    private static final String PASSWORD =
            System.getenv().getOrDefault("ORACLE_PASSWORD", "300806");

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
