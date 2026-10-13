package academy.investmais.ui.processes;

import academy.investmais.enums.AccountType;
import academy.investmais.model.people.Account;
import academy.investmais.model.people.User;
import academy.investmais.model.people.Wallet;
import academy.investmais.repository.AccountRepository;
import academy.investmais.repository.UserRepository;
import academy.investmais.repository.WalletRepository;

import java.util.Scanner;

public class AccountCreationProcess {
    private Scanner scanner = new Scanner(System.in);

    private AccountRepository accountsDatabase;
    private UserRepository userDatabase;
    private WalletRepository walletDatabase;

    public AccountCreationProcess(AccountRepository accountsDatabase, UserRepository userDatabase, WalletRepository walletDatabase) {
        this.accountsDatabase = accountsDatabase;
        this.userDatabase = userDatabase;
        this.walletDatabase = walletDatabase;
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

        //Cria Account
        Account newAccount = new Account(type);

        //Cria User
        User newUser = new User(name, email, password, newAccount.getAccountID());

        //Passa o novo Usuário como o dono da nova conta
        newAccount.setPrimaryOwnerID(newUser.getUserID());

        //Criando Wallet
        Wallet newWallet = new Wallet(newUser.getUserID(), 25000, "CDI/CDB");
        Wallet newWallet2 = new Wallet(newUser.getUserID(), 10000, "Fundos");
        Wallet newWallet3 = new Wallet(newUser.getUserID(), 9000, "Criptomoedas");

        //Para salvar no banco de dados improvisado
        userDatabase.addUserOnDatabase(newUser);

        accountsDatabase.addAccountOnDatabase(newAccount);

        walletDatabase.addWalletOnDatabase(newWallet);
        walletDatabase.addWalletOnDatabase(newWallet2);
        walletDatabase.addWalletOnDatabase(newWallet3);

        System.out.println("Conta " + type + " criada com sucesso!");
    }
}