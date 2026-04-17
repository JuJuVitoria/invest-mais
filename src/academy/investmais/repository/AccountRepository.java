package academy.investmais.repository;

import academy.investmais.model.people.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private List<Account> accountsDatabase = new ArrayList<>();

    public List<Account> getAccountsDatabase() {
        return accountsDatabase;
    }

    public void addAccountOnDatabase(Account account) {
        accountsDatabase.add(account);
    }
}
