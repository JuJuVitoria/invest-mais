package academy.investmais.repository;

import academy.investmais.model.people.Wallet;
import academy.investmais.model.people.User;
import academy.investmais.model.investments.Asset;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void writeReport(String filepath, User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath + ".txt"))) {
            if (walletDatabase.isEmpty()) {
                System.out.println("Nenhum ativo cadastrado!");
                return;
            } else {
                writer.println("===========================================================");
                writer.println("Relatório de ativos de: " + user.getEmail());
                writer.println("===========================================================");
                for (Wallet wallet : this.walletDatabase) {
                    writer.println("-----------------------------------------------------------");
                    writer.println("- Carteira: " + wallet.getName());
                    writer.println("\nID: " + wallet.getWalletID());
                    writer.println("Saldo: " + wallet.getBalance());
                    writer.println("-----------------------------------------------------------");
//                    for (Asset asset : wallet.getAssets()) {
//                        writer.println("Ativo: " + asset.name);
//                        writer.println("Valor: " + asset.totalBalance);
//                        writer.println("\n");
//                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }
}