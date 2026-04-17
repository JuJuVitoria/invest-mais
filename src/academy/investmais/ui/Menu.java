package academy.investmais.ui;

import academy.investmais.enums.AccountType;
import academy.investmais.model.people.Account;
import academy.investmais.model.people.User;
import academy.investmais.repository.AccountRepository;
import academy.investmais.repository.UserRepository;

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
    private String menu = """
            ---------------- MENU TESTE ----------------
            1 - Para exibir o menu novamente
            2 - Criar Conta
            3 - Testar os repositórios
            4 - Consultar Saldo
            5 - Realizar Investimento
            0 - Sair
            Escolha uma opção:
            
            """;

    private AccountRepository accountsDatabase = new AccountRepository();
    private UserRepository userDatabase = new UserRepository();

    public void displayMainMenu() {
        option = -1;
        System.out.println(presentation);
        System.out.println(menu);

         while (option != 0) {
             option = scanner.nextInt();
             processOption(option);
         }
    }

    private void processOption(int option) {
        switch (option) {
            case 1:
                System.out.println(menu);
                break;
            case 2:
                menuCreateAccount();
                break;
            case 3:
                // somente para testar os repositorios
                System.out.println("Para testar os repositórios\n");
                if (userDatabase.getUsersDatabase().isEmpty()) {
                    System.out.println("Nenhum usuário encontrado.");
                } else {
                    System.out.println("\n--- USUÁRIOS CADASTRADAS ---");
                    for (User u : userDatabase.getUsersDatabase()) {
                        System.out.println(" | Nome: " + u.getName() + " | Email: " + u.getEmail());
                    }
                }

                if (accountsDatabase.getAccountsDatabase().isEmpty()) {
                    System.out.println("Nenhum conta encontrado.");
                } else {
                    System.out.println("\n--- CONTAS CADASTRADAS ---");
                    for (Account acc : accountsDatabase.getAccountsDatabase()) {
                        System.out.println("Tipo: " + acc.getAccountType() + " | Dono: " + acc.getPrimaryOwner().getName());
                    }
                }

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
}
