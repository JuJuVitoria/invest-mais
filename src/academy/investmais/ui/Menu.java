package academy.investmais.ui;

import academy.investmais.enums.AccountType;
import academy.investmais.model.people.Account;
import academy.investmais.model.people.User;
import academy.investmais.repository.AccountRepository;
import academy.investmais.repository.UserRepository;
import academy.investmais.service.AuthService;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private int option;
    private String presentation = """
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
            """;
    private String mainMenu = """
            ---------------- MENU TESTE ----------------
            1 - Para exibir o menu novamente
            2 - Consultar Saldo
            3 - Realizar Investimento
            4 - Logout
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

    private AccountRepository accountsDatabase = new AccountRepository();
    private UserRepository userDatabase = new UserRepository();
    private static AuthService auth = new AuthService();

    public void runMenu() {
        System.out.println(presentation);
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

    private void displayAuthMenu() {
        while (!auth.isAuthenticated() && option != 0) {
            System.out.println(authMenu);
            System.out.print("Escolha: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    menuCreateAccount();
                    break;
                case 2:
                    menuLoggin();
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
                System.out.println("Estamos desenvolvendo - Consultar Saldo");
                break;
            case 3:
                System.out.println("Estamos desenvolvendo - Realizar Investimento");
                break;
            case 4:
                System.out.println("Desconectando usuário...");
                auth.logout();
                displayAuthMenu();
                break;
            case 0:
                System.out.println("\nEncessando sistema...");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void menuCreateAccount() {
        System.out.print("Digite o nome do usuário: ");
        String name = scanner.next();
        System.out.print("Digite seu email: ");
        String email = scanner.next();
        System.out.print("Digite sua senha: ");
        String password = scanner.next();

        System.out.print("Qual o tipo da sua conta?\n 1 - PERSONAL\n 2 - BUSINESS \n");
        int accountTypeOption = scanner.nextInt();
        AccountType type = (accountTypeOption == 1) ? AccountType.PERSONAL : AccountType.BUSINESS;

        User newUser = new User(name, email, password);
        Account newAccount = new Account(type, newUser);
        newAccount.addUser(newUser);

        accountsDatabase.addAccountOnDatabase(newAccount);
        userDatabase.addUserOnDatabase(newUser);

        System.out.println("Conta criada com sucesso!\n");
    }

    private void menuLoggin() {
        System.out.println("--------------- LOGIN ---------------");
        System.out.println("Email: ");
        String email = scanner.next();

        System.out.println("Senha: ");
        String password = scanner.next();

        if(auth.login(email, password)) {
            System.out.println("Login efetuado com sucesso!");
        } else {
            System.out.println("Algo deu errado!");
        }
    }
}
