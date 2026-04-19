package academy.investmais.repository;

import academy.investmais.model.people.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WalletRepository {
    private List<Wallet> walletDatabase = new ArrayList<>();

    private AccountRepository accountDatabase = new AccountRepository();

    public WalletRepository() {
        mockWallets();
    }

    private void mockWallets() {
        var accounts = this.accountDatabase.getAccountsDatabase();

    }

    public void addWalletOnDatabase(Wallet wallet) {
        this.walletDatabase.add(wallet);
    }

    public List<Wallet> getWalletDatabase() {
        return this.walletDatabase;
    }
}