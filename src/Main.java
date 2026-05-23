import academy.investmais.enums.AccountStatus;
import academy.investmais.enums.AccountType;
import academy.investmais.model.investments.*;
import academy.investmais.model.people.Account;
import academy.investmais.model.people.User;
import academy.investmais.model.people.Wallet;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            Account account = new Account(AccountType.PERSONAL);
            account.setAccountStatus(AccountStatus.ACTIVE);

            User user = new User(
                    "Tio Patinhas",
                    "patinhosfelizes@email.com",
                    "123456",
                    account.getAccountID()
            );

            account.addUser(user);
            account.setiDPrimaryOwner(user.getUserID());

            Wallet wallet = new Wallet(
                    user.getUserID(),
                    10000,
                    "Carteira Principal"
            );

            wallet.setAssets(new HashMap<>());

            CryptoAsset bitcoin = new CryptoAsset(
                    "Bitcoin",
                    "BTC",
                    450000
            );
            bitcoin.setBlockchain("Bitcoin Network");

            StockAsset acao = new StockAsset(
                    "Patocoin",
                    "PAT34",
                    38.50
            );

            FixedIncomeAsset rendaFixa = new FixedIncomeAsset(
                    "Tesouro Dock",
                    "SELIC",
                    1000
            );

            RealEstateFundsAsset fii = new RealEstateFundsAsset(
                    "XP Mall",
                    "XPML11",
                    105.30
            );

            Investment investment = new Investment(
                    wallet.getWalletID(),
                    bitcoin.getAssetID(),
                    0.5,
                    430000
            );

            System.out.println(account.getAccountType());
            System.out.println(user.getName());
            System.out.println(wallet.getName());

            System.out.println(bitcoin.getAssetInfo());
            System.out.println(acao.getAssetInfo());
            System.out.println(rendaFixa.getAssetInfo());
            System.out.println(fii.getAssetInfo());

            System.out.println("Quantidade investida: " + investment.getQuantity());

        } catch (Exception e) {
            System.out.println("Erro ao executar sistema: " + e.getMessage());
        }
    }
}