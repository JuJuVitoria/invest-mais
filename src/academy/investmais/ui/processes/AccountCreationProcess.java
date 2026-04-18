package academy.investmais.ui.processes;

import academy.investmais.enums.AccountType;
import academy.investmais.model.people.Account;
import academy.investmais.model.people.User;
import academy.investmais.model.people.Wallet;
import academy.investmais.repository.AccountRepository;
import academy.investmais.repository.UserRepository;

import java.util.Scanner;

public class AccountCreationProcess {
    private Scanner scanner = new Scanner(System.in);

    private AccountRepository accountsDatabase;
    private UserRepository userDatabase;

    public AccountCreationProcess(AccountRepository accountsDatabase, UserRepository userDatabase) {
        this.accountsDatabase = accountsDatabase;
        this.userDatabase = userDatabase;
    }

    public void menuCreateAccount() {
        System.out.print("Nome: ");
        String name = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Senha: ");
        String password = scanner.next();

        System.out.print("Tipo: 1-PERSONAL, 2-BUSINESS: ");
        int opt = scanner.nextInt();
        AccountType type = (opt == 1) ? AccountType.PERSONAL : AccountType.BUSINESS;

        //Criando user
        User newUser = new User(name, email, password);
        //Criando Account
        Account newAccount = new Account(type, newUser);
        //Criando Wallet
        Wallet newWallet = new Wallet(newUser.getUserID());

        //Para salvar no banco de dados improvisado
        userDatabase.addUserOnDatabase(newUser);
        accountsDatabase.addAccountOnDatabase(newAccount);

        System.out.println("Conta " + type + " criada com sucesso!");
    }
}