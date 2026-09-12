package academy.investmais.repository;

import academy.investmais.enums.AccountType;
import academy.investmais.model.people.Account;
import academy.investmais.model.people.User;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private List<Account> accountsDatabase = new ArrayList<>();

    public AccountRepository() {
        Account contaTeste = new Account(AccountType.PERSONAL);
        User usuarioTeste = new User("Teste", "teste@email.com", "123", contaTeste.getAccountID());
        contaTeste.setPrimaryOwnerID(usuarioTeste.getUserID());
        UserRepository.addUserOnDatabase(usuarioTeste);

        accountsDatabase.add(contaTeste);
    }

    public List<Account> getAccountsDatabase() {
        return accountsDatabase;
    }

    public void addAccountOnDatabase(Account account) {
        accountsDatabase.add(account);
    }
}