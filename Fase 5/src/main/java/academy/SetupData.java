package academy;

import academy.investmais.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetupData {

    public static void main(String[] args) {

        try (Connection connection = ConnectionFactory.getConnection()) {

            // Desliga o auto-commit: só confirma tudo no banco se TODOS os inserts derem certo.
            connection.setAutoCommit(false);

            try {
                inserirAccountType(connection, 0, "PERSONAL", 1);
                inserirAccountType(connection, 1, "BUSINESS", 100);

                inserirAccountStatus(connection, 0, "ACTIVE");
                inserirAccountStatus(connection, 1, "BLOCKED");
                inserirAccountStatus(connection, 2, "PENDING");

                inserirWalletCategory(connection, 1, "CDI/CDB");
                inserirWalletCategory(connection, 2, "Fundos");
                inserirWalletCategory(connection, 3, "Criptomoedas");
                inserirWalletCategory(connection, 4, "Acoes");

                inserirAccount(connection,
                        "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", 0, 0);

                inserirUser(connection,
                        "11111111-1111-1111-1111-111111111111",
                        "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
                        "Rafael Teste",
                        "rafael.teste@investmais.com",
                        "123456");

                atualizarDonoConta(connection,
                        "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
                        "11111111-1111-1111-1111-111111111111");

                inserirAsset(connection,
                        "51111111-1111-1111-1111-111111111111",
                        "Bitcoin", "BTC", 75949.69, "CRYPTO");
                inserirCryptoAsset(connection,
                        "51111111-1111-1111-1111-111111111111", "Bitcoin");

                inserirAsset(connection,
                        "61111111-1111-1111-1111-111111111111",
                        "Vale", "VALE3", 89.75, "STOCK");
                inserirStockAsset(connection,
                        "61111111-1111-1111-1111-111111111111");

                inserirAsset(connection,
                        "71111111-1111-1111-1111-111111111111",
                        "VGRI11", "VGRI11", 6.74, "REAL_ESTATE");
                inserirRealEstateAsset(connection,
                        "71111111-1111-1111-1111-111111111111");

                // Só confirma no banco se chegou até aqui sem exceção.
                connection.commit();
                System.out.println("Dados iniciais inseridos/confirmados com sucesso!");

            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Erro ao inserir dados, rollback executado: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------
    // Métodos auxiliares: cada um só insere se o registro ainda não existir.
    // ---------------------------------------------------------------

    private static boolean existe(Connection connection, String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    private static void inserirAccountType(Connection connection, int code, String name, int maxUsers) throws SQLException {
        if (existe(connection, "SELECT 1 FROM ACCOUNT_TYPE WHERE code = ?", code)) {
            System.out.println("ACCOUNT_TYPE " + code + " já existe, pulando.");
            return;
        }
        String sql = "INSERT INTO ACCOUNT_TYPE (code, name, maxUsers) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, code);
            statement.setString(2, name);
            statement.setInt(3, maxUsers);
            statement.executeUpdate();
        }
    }

    private static void inserirAccountStatus(Connection connection, int code, String name) throws SQLException {
        if (existe(connection, "SELECT 1 FROM ACCOUNT_STATUS WHERE code = ?", code)) {
            System.out.println("ACCOUNT_STATUS " + code + " já existe, pulando.");
            return;
        }
        String sql = "INSERT INTO ACCOUNT_STATUS (code, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, code);
            statement.setString(2, name);
            statement.executeUpdate();
        }
    }

    private static void inserirWalletCategory(Connection connection, int categoryID, String name) throws SQLException {
        if (existe(connection, "SELECT 1 FROM WALLET_CATEGORY WHERE categoryID = ?", categoryID)) {
            System.out.println("WALLET_CATEGORY " + categoryID + " já existe, pulando.");
            return;
        }
        String sql = "INSERT INTO WALLET_CATEGORY (categoryID, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryID);
            statement.setString(2, name);
            statement.executeUpdate();
        }
    }

    private static void inserirAccount(Connection connection, String accountID, int typeCode, int statusCode) throws SQLException {
        if (existe(connection, "SELECT 1 FROM ACCOUNT WHERE accountID = ?", accountID)) {
            System.out.println("ACCOUNT " + accountID + " já existe, pulando.");
            return;
        }
        String sql = "INSERT INTO ACCOUNT (accountID, idPrimaryOwner, accountTypeCode, accountStatusCode) " +
                "VALUES (?, NULL, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountID);
            statement.setInt(2, typeCode);
            statement.setInt(3, statusCode);
            statement.executeUpdate();
        }
    }

    private static void inserirUser(Connection connection, String userID, String accountID,
                                    String name, String email, String password) throws SQLException {
        if (existe(connection, "SELECT 1 FROM \"USER\" WHERE userID = ?", userID)) {
            System.out.println("USER " + userID + " já existe, pulando.");
            return;
        }
        String sql = "INSERT INTO \"USER\" (userID, accountID, name, email, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            statement.setString(2, accountID);
            statement.setString(3, name);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.executeUpdate();
        }
    }

    private static void atualizarDonoConta(Connection connection, String accountID, String userID) throws SQLException {
        String sql = "UPDATE ACCOUNT SET idPrimaryOwner = ? WHERE accountID = ? AND idPrimaryOwner IS NULL";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userID);
            statement.setString(2, accountID);
            statement.executeUpdate();
        }
    }

    private static void inserirAsset(Connection connection, String assetID, String name, String ticker,
                                     double currentValue, String type) throws SQLException {
        if (existe(connection, "SELECT 1 FROM ASSET WHERE assetID = ?", assetID)) {
            System.out.println("ASSET " + assetID + " já existe, pulando.");
            return;
        }
        String sql = "INSERT INTO ASSET (assetID, assetName, assetTicker, currentValue, assetType) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, assetID);
            statement.setString(2, name);
            statement.setString(3, ticker);
            statement.setDouble(4, currentValue);
            statement.setString(5, type);
            statement.executeUpdate();
        }
    }

    private static void inserirCryptoAsset(Connection connection, String assetID, String blockchain) throws SQLException {
        if (existe(connection, "SELECT 1 FROM CRYPTO_ASSET WHERE assetID = ?", assetID)) {
            return;
        }
        String sql = "INSERT INTO CRYPTO_ASSET (assetID, blockchain) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, assetID);
            statement.setString(2, blockchain);
            statement.executeUpdate();
        }
    }

    private static void inserirStockAsset(Connection connection, String assetID) throws SQLException {
        if (existe(connection, "SELECT 1 FROM STOCK_ASSET WHERE assetID = ?", assetID)) {
            return;
        }
        String sql = "INSERT INTO STOCK_ASSET (assetID) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, assetID);
            statement.executeUpdate();
        }
    }

    private static void inserirRealEstateAsset(Connection connection, String assetID) throws SQLException {
        if (existe(connection, "SELECT 1 FROM REAL_ESTATE_FUNDS_ASSET WHERE assetID = ?", assetID)) {
            return;
        }
        String sql = "INSERT INTO REAL_ESTATE_FUNDS_ASSET (assetID) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, assetID);
            statement.executeUpdate();
        }
    }
}