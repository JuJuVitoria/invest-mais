package academy.investmais.ui;

import academy.Main;
import academy.investmais.model.people.Wallet;
import academy.investmais.model.people.User;
import academy.investmais.repository.AccountRepository;
import academy.investmais.repository.UserRepository;
import academy.investmais.repository.WalletRepository;
import academy.investmais.service.AuthService;
import academy.investmais.ui.processes.AccountCreationProcess;
import academy.investmais.ui.processes.MenuLoginProcess;


import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private int option;
    private String mainMenu = """
            ---------------- MENU TESTE ----------------
            1 - Para exibir o menu novamente
            2 - Consultar Saldo
            3 - Realizar Investimento
            4 - Gerar Relatório de Ativos
            5 - Logout
            6 - Banco de Dados Oracle (Wallet)
            0 - Encerrar aplicação
            Escolha uma opção:
            
            """;
    private String authMenu = """
            --------------- AUTENTICAÇÃO ---------------
            
            Você ainda não possui cadastrado?
            1 - Se cadastrar
            
            Você já possui cadastro?
            2 - Login
            
            Deseja encerrar a aplicação?
            0 - Encerrar
            
            """;

    private static AuthService auth = new AuthService();
    private UserRepository userDatabase = new UserRepository();
    private AccountRepository accountsDatabase = new AccountRepository();
    private WalletRepository walletDatabase = new WalletRepository();

    public void runMenu() {
        System.out.println("""
                ------------ INVEST MAIS -------------
                Este é um trabalha realizado durante estudados da FIAP...
            
                Integrantes
                - Julia Vitoria da Luz
                - Rafael Costa dos Santos
                - Murillo Mendes Rodrigues
                - Michael Stephan Silva Gebenlian
                - Alvaro Gama Bastos Rangel
            
                Esperamos que você goste!!
            
                Iniciando sistema...
                
                """);
        option = -1;
        boolean systemRunning = true;

        while (systemRunning) {
            if (!auth.isAuthenticated()) {
                displayAuthMenu();
                if (option == 0) systemRunning = false;
            } else {
                displayMainMenu();
                if (option == 0 && auth.isAuthenticated()) systemRunning = false;
            }
        }
    }

    private void displayAssets() {
         System.out.println("""
               1 - Ações
               2 - Criptomoedas
               3 - Fundos
               4 - CDI/CDB
         """);

         option = scanner.nextInt();

         var user = auth.getLoggedUser();
         Wallet wallet = new Wallet(user.getUserID(), 1000);

         switch (option) {
             case 1:
                 wallet.setName("Ações");
                 walletDatabase.addWalletOnDatabase(wallet);
                 break;
             case 2:
                 wallet.setName("Criptomoedas");
                 walletDatabase.addWalletOnDatabase(wallet);
                 break;
             case 3:
                 wallet.setName("Fundos");
                 walletDatabase.addWalletOnDatabase(wallet);
                 break;
             case 4:
                 wallet.setName("CDI/CDB");
                 walletDatabase.addWalletOnDatabase(wallet);
                 break;
             default:


                 break;
         }
    }

    private void displayInvestmentMenu() {
        int investmentOption = -1;

        while (investmentOption != 0) {
            System.out.println("""
                --------------- ÁREA DE INVESTIMENTOS ---------------
                1 - Ver minha carteira (Meus Ativos)
                2 - Explorar Mercado (Investir)
                0 - Voltar ao Menu Principal
                -----------------------------------------------------
                """);
            System.out.print("Escolha uma opção: ");
            investmentOption = scanner.nextInt();

            switch (investmentOption) {
                case 1:
                    System.out.println("Exibindo sua Wallet e ativos...");
                    break;
                case 2:
                    System.out.println("Listando ativos do AssetRepository...");
                    displayAssets();
                    break;

                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void displayBalance() {
        var wallets = this.walletDatabase.getWalletDatabase();

        System.out.println("Consultando wallets...\n\n");
        wallets.forEach(wallet -> {
            System.out.print("Wallet ID: ");
            System.out.println(wallet.getWalletID());
            System.out.print("Nome do Investimento: ");
            System.out.println(wallet.getName());
            System.out.print("Saldo: ");
            System.out.println(wallet.getBalance());
            System.out.println("\n");
        });
    }

    private void generateReport(User user) {
        System.out.println("Digite o nome do arquivo para continuar:");
        var filename = scanner.next();
        this.walletDatabase.writeReport(filename, user);
    }

    private void displayAuthMenu() {
        while (!auth.isAuthenticated() && option != 0) {
            System.out.println(authMenu);
            System.out.print("Escolha: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    AccountCreationProcess accountCreation = new AccountCreationProcess(this.accountsDatabase, this.userDatabase, this.walletDatabase);
                    accountCreation.menuCreateAccount();
                    break;
                case 2:
                    MenuLoginProcess loginProcess = new MenuLoginProcess(auth);
                    loginProcess.menuLogin();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void displayMainMenu() {
        option = -1;
        System.out.println(mainMenu);

         while (option != 0) {
             option = scanner.nextInt();
             mainProcessOption(option);
         }
    }

    private void mainProcessOption(int option) {
        switch (option) {
            case 1:
                System.out.println(mainMenu);
                break;
            case 2:
                displayBalance();
                System.out.println(mainMenu);
                break;
            case 3:
                displayInvestmentMenu();
                System.out.println(mainMenu);
                break;
            case 4:
                generateReport(auth.getLoggedUser());
                System.out.println(mainMenu);
                break;
            case 5:
                System.out.println("Desconectando usuário...");
                auth.logout();
                displayAuthMenu();
                break;
            case 6:
                displayOracleWalletMenu();
                System.out.println(mainMenu);
                break;
            case 0:
                System.out.println("\nEncessando sistema...");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void displayOracleWalletMenu() {
        int oracleOption = -1;

        while (oracleOption != 0) {
        System.out.println("""
        --------------- BANCO ORACLE - WALLET ---------------
        1 - Inserir carteira
        2 - Listar / exibir carteiras
        3 - Alterar carteira
        4 - Excluir carteira
        5 - Listar categorias
        6 - Lista tipos de conta
        7 - Lista status de conta
        8 - Lista contas
        9 - Testar todas as tabelas (INSERT, UPDATE, DELETE, SELECT)
        0 - Voltar ao Menu Principal
        -----------------------------------------------------
        """);
            System.out.print("Escolha uma opção: ");
            oracleOption = scanner.nextInt();
            scanner.nextLine();

            switch (oracleOption) {
                case 1:
                    Main.testarInserir();
                    break;
                case 2:
                    Main.testarExibir();
                    break;
                case 3:
                    Main.testarAtualizar();
                    break;
                case 4:
                    Main.testarExcluir();
                    break;
                case 5:
                    Main.testarListarCategorias();
                    break;
                case 6:
                    Main.testarListarTiposDeConta();
                    break;  
                case 7:
                    Main.testarListarStatusDeConta();
                    break;
                case 8:
                    Main.testarListarContas();
                    break;
                case 9:
                    Main.testarIntegracaoCompleta();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    }
        }
    }
}
