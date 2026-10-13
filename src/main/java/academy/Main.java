package academy;

import academy.investmais.database.WalletCategoryDAO;
import academy.investmais.database.WalletDAO;
import academy.investmais.model.people.Wallet;
import academy.investmais.model.people.WalletCategory;
import academy.investmais.ui.Menu;
import academy.investmais.database.AccountTypeDAO;
import academy.investmais.model.people.AccountTypeRecord;
import academy.investmais.database.AccountStatusDAO;
import academy.investmais.model.people.AccountStatusRecord;
import academy.investmais.database.AccountDAO;
import academy.investmais.database.AssetDAO;
import academy.investmais.database.CryptoAssetDAO;
import academy.investmais.database.FixedIncomeAssetDAO;
import academy.investmais.database.RealEstateFundsAssetDAO;
import academy.investmais.database.StockAssetDAO;
import academy.investmais.database.UserDAO;
import academy.investmais.database.WalletAssetDAO;
import academy.investmais.enums.AccountStatus;
import academy.investmais.enums.AccountType;
import academy.investmais.model.investments.Asset;
import academy.investmais.model.investments.CryptoAsset;
import academy.investmais.model.investments.FixedIncomeAsset;
import academy.investmais.model.investments.RealEstateFundsAsset;
import academy.investmais.model.investments.StockAsset;
import academy.investmais.model.investments.WalletAsset;
import academy.investmais.model.people.Account;
import academy.investmais.model.people.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String USER_ORACLE = "11111111-1111-1111-1111-111111111111";
    private static final int CODIGO_TESTE = 99;
    private static final String ACCOUNT_TESTE = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaa01";
    private static final String USER_TESTE = "11111111-1111-1111-1111-111111111101";
    private static final String WALLET_TESTE = "21111111-1111-1111-1111-111111111101";
    private static final String ASSET_CRYPTO_TESTE = "51111111-1111-1111-1111-111111111101";
    private static final String ASSET_STOCK_TESTE = "61111111-1111-1111-1111-111111111101";
    private static final String ASSET_FIXED_TESTE = "71111111-1111-1111-1111-111111111101";
    private static final String ASSET_REAL_TESTE = "81111111-1111-1111-1111-111111111101";

    private static final WalletDAO walletDAO = new WalletDAO();
    private static final WalletCategoryDAO categoryDAO = new WalletCategoryDAO();
    private static final AccountTypeDAO accountTypeDAO = new AccountTypeDAO();
    private static final AccountStatusDAO accountStatusDAO = new AccountStatusDAO();
    private static final AccountDAO accountDAO = new AccountDAO();
    private static final UserDAO userDAO = new UserDAO();
    private static final AssetDAO assetDAO = new AssetDAO();
    private static final CryptoAssetDAO cryptoAssetDAO = new CryptoAssetDAO();
    private static final StockAssetDAO stockAssetDAO = new StockAssetDAO();
    private static final FixedIncomeAssetDAO fixedIncomeAssetDAO = new FixedIncomeAssetDAO();
    private static final RealEstateFundsAssetDAO realEstateFundsAssetDAO = new RealEstateFundsAssetDAO();
    private static final WalletAssetDAO walletAssetDAO = new WalletAssetDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.runMenu();
    }

    public static void testarInserir() {
        System.out.println("\n=== INSERIR CARTEIRA (ORACLE) ===");

        System.out.print("Nome da carteira: ");
        String name = scanner.nextLine();

        System.out.print("Saldo: ");
        long balance = lerLong();

        System.out.print("Categoria (1=CDI/CDB, 2=Fundos, 3=Criptomoedas, 4=Acoes): ");
        int categoryID = lerInt();
        scanner.nextLine();

        Wallet wallet = new Wallet(USER_ORACLE, balance, name, categoryID);

        try {
            walletDAO.inserir(wallet);
            System.out.println("[INSERT] Carteira inserida: " + formatar(wallet));
        } catch (SQLException e) {
            System.err.println("Erro ao inserir carteira: " + e.getMessage());
        }
    }

    public static void testarExibir() {
        System.out.println("\n=== EXIBIR CARTEIRAS (ORACLE) ===");

        System.out.println("1 - Listar todas");
        System.out.println("2 - Buscar por ID");
        System.out.print("Escolha: ");
        int escolha = lerInt();
        scanner.nextLine();

        try {
            if (escolha == 2) {
                System.out.print("ID da carteira: ");
                String walletID = scanner.nextLine();
                Wallet encontrada = walletDAO.buscarPorId(walletID);
                System.out.println("[SELECT] " + formatar(encontrada));
                return;
            }

            List<Wallet> wallets = walletDAO.listarTodos();
            System.out.println("[SELECT] Total de carteiras: " + wallets.size());
            for (Wallet item : wallets) {
                System.out.println(" - " + formatar(item));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao exibir carteiras: " + e.getMessage());
        }
    }

    public static void testarAtualizar() {
        System.out.println("\n=== ALTERAR CARTEIRA (ORACLE) ===");

        try {
            listarResumo();

            System.out.print("ID da carteira que deseja alterar: ");
            String walletID = scanner.nextLine();

            Wallet wallet = walletDAO.buscarPorId(walletID);
            if (wallet == null) {
                System.out.println("[SELECT] registro não encontrado");
                return;
            }

            System.out.println("Atual: " + formatar(wallet));

            System.out.print("Novo nome: ");
            String name = scanner.nextLine();

            System.out.print("Novo saldo: ");
            long balance = lerLong();

            System.out.print("Nova categoria (1=CDI/CDB, 2=Fundos, 3=Criptomoedas, 4=Acoes): ");
            int categoryID = lerInt();
            scanner.nextLine();

            wallet.setName(name);
            wallet.setBalance(balance);
            wallet.setCategoryID(categoryID);

            walletDAO.atualizar(wallet);
            System.out.println("[UPDATE] Carteira atualizada: " + formatar(walletDAO.buscarPorId(walletID)));
        } catch (SQLException e) {
            System.err.println("Erro ao alterar carteira: " + e.getMessage());
        }
    }

    public static void testarExcluir() {
        System.out.println("\n=== EXCLUIR CARTEIRA (ORACLE) ===");

        try {
            listarResumo();

            System.out.print("ID da carteira que deseja excluir: ");
            String walletID = scanner.nextLine();

            Wallet encontrada = walletDAO.buscarPorId(walletID);
            if (encontrada == null) {
                System.out.println("[SELECT] registro não encontrado");
                return;
            }

            walletDAO.excluir(walletID);
            System.out.println("[DELETE] Carteira excluída: " + encontrada.getName());

            Wallet aposDelete = walletDAO.buscarPorId(walletID);
            System.out.println("[SELECT] Após DELETE: "
                    + (aposDelete == null ? "registro não encontrado (OK)" : "registro ainda existe"));
        } catch (SQLException e) {
            System.err.println("Erro ao excluir carteira: " + e.getMessage());
        }
    }

    public static void testarListarCategorias() {
        System.out.println("\n=== LISTAR CATEGORIAS (ORACLE) ===");

        try {
            List<WalletCategory> categories = categoryDAO.listarTodos();
            System.out.println("[SELECT] Total de categorias: " + categories.size());
            for (WalletCategory category : categories) {
                System.out.println(" - " + category.getCategoryID() + " | " + category.getName());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias: " + e.getMessage());
        }
    }
    public static void testarListarTiposDeConta() {
    System.out.println("\n=== LISTAR TIPOS DE CONTA (ORACLE) ===");
    try {
        List<AccountTypeRecord> accountTypes = accountTypeDAO.listarTodos();
        System.out.println("[SELECT] Total de tipos de conta: " + accountTypes.size());
        for (AccountTypeRecord type : accountTypes) {
            System.out.println(" - " + type.getCode() + " | " + type.getName() + " | maxUsers=" + type.getMaxUsers());
        }
        } catch (SQLException e) {
        System.err.println("Erro ao listar tipos de conta: " + e.getMessage());
    }
}
public static void testarListarStatusDeConta() {
    System.out.println("\n=== LISTAR STATUS DE CONTA (ORACLE) ===");
    try {
        List<AccountStatusRecord> accountStatuses = accountStatusDAO.listarTodos();
        System.out.println("[SELECT] Total de status de conta: " + accountStatuses.size());
        for (AccountStatusRecord status : accountStatuses) {
            System.out.println(" - " + status.getCode() + " | " + status.getName());
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar status de conta: " + e.getMessage());
    }
}
public static void testarListarContas() {
    System.out.println("\n=== LISTAR CONTAS (ORACLE) ===");
    try {
        List<Account> accounts = accountDAO.listarTodos();
        System.out.println("[SELECT] Total de contas: " + accounts.size());
        for (Account account : accounts) {
           System.out.println(" - " + account.getAccountID()
        + " | tipo=" + account.getAccountType()
        + " | status=" + account.getAccountStatus()
        + " | dono=" + account.getPrimaryOwnerID());
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar contas: " + e.getMessage());
    }
}   
private static void listarResumo() throws SQLException {
        List<Wallet> wallets = walletDAO.listarTodos();
        if (wallets.isEmpty()) {
            System.out.println("Nenhuma carteira cadastrada no Oracle.");
            return;
        }

        System.out.println("Carteiras no banco:");
        for (Wallet item : wallets) {
            System.out.println(" - " + item.getWalletID() + " | " + item.getName() + " | saldo=" + item.getBalance());
        }
    }
    

    private static int lerInt() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Digite um número válido: ");
        }
        return scanner.nextInt();
    }

    private static long lerLong() {
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.print("Digite um número válido: ");
        }
        return scanner.nextLong();
    }

    private static String formatar(Wallet wallet) {
        if (wallet == null) {
            return "registro não encontrado";
        }

        return "Wallet{id='" + wallet.getWalletID()
                + "', categoryID=" + wallet.getCategoryID()
                + ", userID='" + wallet.getUserID()
                + "', name='" + wallet.getName()
                + "', totalBalance=" + wallet.getBalance()
                + "}";
    }

    public static void testarIntegracaoCompleta() {
        System.out.println("\n=== TESTE JDBC EM TODAS AS TABELAS ===");
        try {
            System.out.println("Removendo registros de teste anteriores, se existirem...");
            limparDadosDeTeste();
            testarAccountType();
            testarAccountStatus();
            testarWalletCategory();
            testarAccountEUser();
            testarWallet();
            testarAssets();
            testarWalletAsset();
            System.out.println("\n--- DELETE ---");
            limparDadosDeTeste();
            conferirExclusao();
            System.out.println("\nINSERT, UPDATE, DELETE e SELECT executados em todas as tabelas.");
        } catch (SQLException e) {
            System.err.println("Erro no teste de integração: " + e.getMessage());
            try {
                limparDadosDeTeste();
            } catch (SQLException limpeza) {
                System.err.println("Erro ao limpar dados de teste: " + limpeza.getMessage());
            }
        }
    }

    private static void testarAccountType() throws SQLException {
        System.out.println("\n-- ACCOUNT_TYPE --");
        AccountTypeRecord tipo = new AccountTypeRecord(CODIGO_TESTE, "TESTE", 3);
        accountTypeDAO.inserir(tipo);

        AccountTypeRecord lido = accountTypeDAO.buscarPorId(CODIGO_TESTE);
        conferir(lido != null && "TESTE".equals(lido.getName()), "INSERT/SELECT ACCOUNT_TYPE");

        tipo.setName("TESTE_UPD");
        tipo.setMaxUsers(4);
        accountTypeDAO.atualizar(tipo);
        lido = accountTypeDAO.buscarPorId(CODIGO_TESTE);
        conferir(lido != null && "TESTE_UPD".equals(lido.getName()) && lido.getMaxUsers() == 4,
                "UPDATE/SELECT ACCOUNT_TYPE");
        conferir(contemCodigo(accountTypeDAO.listarTodos()), "SELECT ACCOUNT_TYPE listarTodos");
    }

    private static void testarAccountStatus() throws SQLException {
        System.out.println("\n-- ACCOUNT_STATUS --");
        AccountStatusRecord status = new AccountStatusRecord(CODIGO_TESTE, "TESTE");
        accountStatusDAO.inserir(status);

        AccountStatusRecord lido = accountStatusDAO.buscarPorId(CODIGO_TESTE);
        conferir(lido != null && "TESTE".equals(lido.getName()), "INSERT/SELECT ACCOUNT_STATUS");

        status.setName("TESTE_UPD");
        accountStatusDAO.atualizar(status);
        lido = accountStatusDAO.buscarPorId(CODIGO_TESTE);
        conferir(lido != null && "TESTE_UPD".equals(lido.getName()), "UPDATE/SELECT ACCOUNT_STATUS");

        boolean achou = false;
        for (AccountStatusRecord item : accountStatusDAO.listarTodos()) {
            if (item.getCode() == CODIGO_TESTE) {
                achou = true;
                break;
            }
        }
        conferir(achou, "SELECT ACCOUNT_STATUS listarTodos");
    }

    private static void testarWalletCategory() throws SQLException {
        System.out.println("\n-- WALLET_CATEGORY --");
        WalletCategory categoria = new WalletCategory(CODIGO_TESTE, "TESTE");
        categoryDAO.inserir(categoria);

        WalletCategory lida = categoryDAO.buscarPorId(CODIGO_TESTE);
        conferir(lida != null && "TESTE".equals(lida.getName()), "INSERT/SELECT WALLET_CATEGORY");

        categoria.setName("TESTE_UPD");
        categoryDAO.atualizar(categoria);
        lida = categoryDAO.buscarPorId(CODIGO_TESTE);
        conferir(lida != null && "TESTE_UPD".equals(lida.getName()), "UPDATE/SELECT WALLET_CATEGORY");

        boolean achou = false;
        for (WalletCategory item : categoryDAO.listarTodos()) {
            if (item.getCategoryID() == CODIGO_TESTE) {
                achou = true;
                break;
            }
        }
        conferir(achou, "SELECT WALLET_CATEGORY listarTodos");
    }

    private static void testarAccountEUser() throws SQLException {
        System.out.println("\n-- ACCOUNT e USER --");
        Account account = new Account(AccountType.PERSONAL);
        account.setAccountID(ACCOUNT_TESTE);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setPrimaryOwnerID(null);
        accountDAO.inserir(account);

        Account contaLida = accountDAO.buscarPorId(ACCOUNT_TESTE);
        conferir(contaLida != null && contaLida.getPrimaryOwnerID() == null, "INSERT/SELECT ACCOUNT");

        User user = new User("Usuario Fase 6", "fase6.teste@investmais.com", "123456", ACCOUNT_TESTE);
        user.setUserID(USER_TESTE);
        userDAO.inserir(user);

        User userLido = userDAO.buscarPorId(USER_TESTE);
        conferir(userLido != null && "Usuario Fase 6".equals(userLido.getName()), "INSERT/SELECT USER");

        user.setName("Usuario Fase 6 Atualizado");
        user.setEmail("fase6.atualizado@investmais.com");
        user.setPassword("654321");
        userDAO.atualizar(user);
        userLido = userDAO.buscarPorId(USER_TESTE);
        conferir(userLido != null
                        && "Usuario Fase 6 Atualizado".equals(userLido.getName())
                        && "654321".equals(userLido.getPassword()),
                "UPDATE/SELECT USER");

        account.setPrimaryOwnerID(USER_TESTE);
        account.setAccountStatus(AccountStatus.BLOCKED);
        accountDAO.atualizar(account);
        contaLida = accountDAO.buscarPorId(ACCOUNT_TESTE);
        conferir(contaLida != null
                        && USER_TESTE.equals(contaLida.getPrimaryOwnerID())
                        && contaLida.getAccountStatus() == AccountStatus.BLOCKED,
                "UPDATE/SELECT ACCOUNT");

        boolean achouConta = false;
        for (Account item : accountDAO.listarTodos()) {
            if (ACCOUNT_TESTE.equals(item.getAccountID())) {
                achouConta = true;
                break;
            }
        }
        conferir(achouConta, "SELECT ACCOUNT listarTodos");

        boolean achouUsuario = false;
        for (User item : userDAO.listarTodos()) {
            if (USER_TESTE.equals(item.getUserID())) {
                achouUsuario = true;
                break;
            }
        }
        conferir(achouUsuario, "SELECT USER listarTodos");
    }

    private static void testarWallet() throws SQLException {
        System.out.println("\n-- WALLET --");
        Wallet wallet = new Wallet(USER_TESTE, 1000, "Carteira Fase 6", CODIGO_TESTE);
        wallet.setWalletID(WALLET_TESTE);
        walletDAO.inserir(wallet);

        Wallet lida = walletDAO.buscarPorId(WALLET_TESTE);
        conferir(lida != null && "Carteira Fase 6".equals(lida.getName()), "INSERT/SELECT WALLET");

        wallet.setName("Carteira Fase 6 Atualizada");
        wallet.setBalance(1500);
        walletDAO.atualizar(wallet);
        lida = walletDAO.buscarPorId(WALLET_TESTE);
        conferir(lida != null && "Carteira Fase 6 Atualizada".equals(lida.getName()) && lida.getBalance() == 1500,
                "UPDATE/SELECT WALLET");

        boolean achou = false;
        for (Wallet item : walletDAO.listarTodos()) {
            if (WALLET_TESTE.equals(item.getWalletID())) {
                achou = true;
                break;
            }
        }
        conferir(achou, "SELECT WALLET listarTodos");
    }

    private static void testarAssets() throws SQLException {
        System.out.println("\n-- ASSET e especializacoes --");

        CryptoAsset crypto = new CryptoAsset("Cripto Teste", "CRPT", 200);
        crypto.setAssetID(ASSET_CRYPTO_TESTE);
        crypto.setBlockchain("Bitcoin");
        assetDAO.inserir(crypto);
        cryptoAssetDAO.inserir(crypto);
        crypto.setAssetName("Cripto Teste Atualizado");
        crypto.setCurrentValue(210);
        crypto.setBlockchain("Lightning");
        assetDAO.atualizar(crypto);
        cryptoAssetDAO.atualizar(crypto);

        Asset cryptoBase = assetDAO.buscarPorId(ASSET_CRYPTO_TESTE);
        CryptoAsset cryptoLido = cryptoAssetDAO.buscarPorId(ASSET_CRYPTO_TESTE);
        conferir(cryptoBase != null && "Cripto Teste Atualizado".equals(cryptoBase.getAssetName()),
                "INSERT/UPDATE/SELECT ASSET (CRYPTO)");
        conferir(cryptoLido != null && "Lightning".equals(cryptoLido.getBlockchain()),
                "INSERT/UPDATE/SELECT CRYPTO_ASSET");

        StockAsset stock = new StockAsset("Acao Teste", "TEST3", 10);
        stock.setAssetID(ASSET_STOCK_TESTE);
        assetDAO.inserir(stock);
        stockAssetDAO.inserir(stock);
        stock.setAssetName("Acao Teste Atualizada");
        stock.setCurrentValue(11);
        assetDAO.atualizar(stock);
        stockAssetDAO.atualizar(stock);

        Asset acao = assetDAO.buscarPorId(ASSET_STOCK_TESTE);
        conferir(acao != null && "Acao Teste Atualizada".equals(acao.getAssetName())
                        && stockAssetDAO.buscarPorId(ASSET_STOCK_TESTE) != null,
                "INSERT/UPDATE/SELECT STOCK_ASSET");

        FixedIncomeAsset fixed = new FixedIncomeAsset("CDB Teste", "CDBT", 100);
        fixed.setAssetID(ASSET_FIXED_TESTE);
        assetDAO.inserir(fixed);
        fixedIncomeAssetDAO.inserir(fixed);
        fixed.setAssetName("CDB Teste Atualizado");
        fixed.setCurrentValue(105);
        assetDAO.atualizar(fixed);
        fixedIncomeAssetDAO.atualizar(fixed);

        Asset rendaFixa = assetDAO.buscarPorId(ASSET_FIXED_TESTE);
        conferir(rendaFixa != null && "CDB Teste Atualizado".equals(rendaFixa.getAssetName())
                        && fixedIncomeAssetDAO.buscarPorId(ASSET_FIXED_TESTE) != null,
                "INSERT/UPDATE/SELECT FIXED_INCOME_ASSET");

        RealEstateFundsAsset realEstate = new RealEstateFundsAsset("FII Teste", "TEST11", 50);
        realEstate.setAssetID(ASSET_REAL_TESTE);
        assetDAO.inserir(realEstate);
        realEstateFundsAssetDAO.inserir(realEstate);
        realEstate.setAssetName("FII Teste Atualizado");
        realEstate.setCurrentValue(55);
        assetDAO.atualizar(realEstate);
        realEstateFundsAssetDAO.atualizar(realEstate);

        Asset fii = assetDAO.buscarPorId(ASSET_REAL_TESTE);
        conferir(fii != null && "FII Teste Atualizado".equals(fii.getAssetName())
                        && realEstateFundsAssetDAO.buscarPorId(ASSET_REAL_TESTE) != null,
                "INSERT/UPDATE/SELECT REAL_ESTATE_FUNDS_ASSET");

        int ativosEncontrados = 0;
        for (Asset item : assetDAO.listarTodos()) {
            if (ASSET_CRYPTO_TESTE.equals(item.getAssetID())
                    || ASSET_STOCK_TESTE.equals(item.getAssetID())
                    || ASSET_FIXED_TESTE.equals(item.getAssetID())
                    || ASSET_REAL_TESTE.equals(item.getAssetID())) {
                ativosEncontrados++;
            }
        }
        conferir(ativosEncontrados == 4, "SELECT ASSET listarTodos");
        conferir(contemAssetId(cryptoAssetDAO.listarTodos(), ASSET_CRYPTO_TESTE), "SELECT CRYPTO_ASSET listarTodos");
        conferir(contemAssetId(stockAssetDAO.listarTodos(), ASSET_STOCK_TESTE), "SELECT STOCK_ASSET listarTodos");
        conferir(contemAssetId(fixedIncomeAssetDAO.listarTodos(), ASSET_FIXED_TESTE),
                "SELECT FIXED_INCOME_ASSET listarTodos");
        conferir(contemAssetId(realEstateFundsAssetDAO.listarTodos(), ASSET_REAL_TESTE),
                "SELECT REAL_ESTATE_FUNDS_ASSET listarTodos");
    }

    private static void testarWalletAsset() throws SQLException {
        System.out.println("\n-- WALLET_ASSET --");
        WalletAsset posicao = new WalletAsset(WALLET_TESTE, ASSET_CRYPTO_TESTE, 1.5);
        walletAssetDAO.inserir(posicao);

        WalletAsset lida = walletAssetDAO.buscarPorId(WALLET_TESTE, ASSET_CRYPTO_TESTE);
        conferir(lida != null && Math.abs(lida.getQuantity() - 1.5) < 0.0001, "INSERT/SELECT WALLET_ASSET");

        posicao.setQuantity(2.5);
        walletAssetDAO.atualizar(posicao);
        lida = walletAssetDAO.buscarPorId(WALLET_TESTE, ASSET_CRYPTO_TESTE);
        conferir(lida != null && Math.abs(lida.getQuantity() - 2.5) < 0.0001, "UPDATE/SELECT WALLET_ASSET");

        boolean achou = false;
        for (WalletAsset item : walletAssetDAO.listarTodos()) {
            if (WALLET_TESTE.equals(item.getWalletID()) && ASSET_CRYPTO_TESTE.equals(item.getAssetID())) {
                achou = true;
                break;
            }
        }
        conferir(achou, "SELECT WALLET_ASSET listarTodos");
    }

    private static void conferirExclusao() throws SQLException {
        conferir(accountTypeDAO.buscarPorId(CODIGO_TESTE) == null, "DELETE/SELECT ACCOUNT_TYPE");
        conferir(accountStatusDAO.buscarPorId(CODIGO_TESTE) == null, "DELETE/SELECT ACCOUNT_STATUS");
        conferir(accountDAO.buscarPorId(ACCOUNT_TESTE) == null, "DELETE/SELECT ACCOUNT");
        conferir(userDAO.buscarPorId(USER_TESTE) == null, "DELETE/SELECT USER");
        conferir(categoryDAO.buscarPorId(CODIGO_TESTE) == null, "DELETE/SELECT WALLET_CATEGORY");
        conferir(walletDAO.buscarPorId(WALLET_TESTE) == null, "DELETE/SELECT WALLET");
        conferir(assetDAO.buscarPorId(ASSET_CRYPTO_TESTE) == null, "DELETE/SELECT ASSET CRYPTO");
        conferir(assetDAO.buscarPorId(ASSET_STOCK_TESTE) == null, "DELETE/SELECT ASSET STOCK");
        conferir(assetDAO.buscarPorId(ASSET_FIXED_TESTE) == null, "DELETE/SELECT ASSET FIXED_INCOME");
        conferir(assetDAO.buscarPorId(ASSET_REAL_TESTE) == null, "DELETE/SELECT ASSET REAL_ESTATE");
        conferir(cryptoAssetDAO.buscarPorId(ASSET_CRYPTO_TESTE) == null, "DELETE/SELECT CRYPTO_ASSET");
        conferir(stockAssetDAO.buscarPorId(ASSET_STOCK_TESTE) == null, "DELETE/SELECT STOCK_ASSET");
        conferir(fixedIncomeAssetDAO.buscarPorId(ASSET_FIXED_TESTE) == null, "DELETE/SELECT FIXED_INCOME_ASSET");
        conferir(realEstateFundsAssetDAO.buscarPorId(ASSET_REAL_TESTE) == null, "DELETE/SELECT REAL_ESTATE_FUNDS_ASSET");
        conferir(walletAssetDAO.buscarPorId(WALLET_TESTE, ASSET_CRYPTO_TESTE) == null, "DELETE/SELECT WALLET_ASSET");
    }

    private static void limparDadosDeTeste() throws SQLException {
        walletAssetDAO.excluir(WALLET_TESTE, ASSET_CRYPTO_TESTE);
        cryptoAssetDAO.excluir(ASSET_CRYPTO_TESTE);
        stockAssetDAO.excluir(ASSET_STOCK_TESTE);
        fixedIncomeAssetDAO.excluir(ASSET_FIXED_TESTE);
        realEstateFundsAssetDAO.excluir(ASSET_REAL_TESTE);
        assetDAO.excluir(ASSET_CRYPTO_TESTE);
        assetDAO.excluir(ASSET_STOCK_TESTE);
        assetDAO.excluir(ASSET_FIXED_TESTE);
        assetDAO.excluir(ASSET_REAL_TESTE);
        walletDAO.excluir(WALLET_TESTE);

        Account account = accountDAO.buscarPorId(ACCOUNT_TESTE);
        if (account != null) {
            account.setPrimaryOwnerID(null);
            account.setAccountType(AccountType.PERSONAL);
            account.setAccountStatus(AccountStatus.ACTIVE);
            accountDAO.atualizar(account);
        }

        userDAO.excluir(USER_TESTE);
        accountDAO.excluir(ACCOUNT_TESTE);
        categoryDAO.excluir(CODIGO_TESTE);
        accountStatusDAO.excluir(CODIGO_TESTE);
        accountTypeDAO.excluir(CODIGO_TESTE);
    }

    private static boolean contemCodigo(List<AccountTypeRecord> tipos) {
        for (AccountTypeRecord item : tipos) {
            if (item.getCode() == CODIGO_TESTE) {
                return true;
            }
        }
        return false;
    }

    private static boolean contemAssetId(List<? extends Asset> assets, String assetID) {
        for (Asset item : assets) {
            if (assetID.equals(item.getAssetID())) {
                return true;
            }
        }
        return false;
    }

    private static void conferir(boolean ok, String mensagem) throws SQLException {
        if (!ok) {
            throw new SQLException(mensagem);
        }
        System.out.println("[OK] " + mensagem);
    }
}