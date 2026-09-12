package academy;

import academy.investmais.database.WalletDAO;
import academy.investmais.model.people.Wallet;
import academy.investmais.ui.Menu;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String USER_ORACLE = "11111111-1111-1111-1111-111111111111";
    private static final WalletDAO walletDAO = new WalletDAO();
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
}
