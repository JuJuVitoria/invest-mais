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
            Menu menu = new Menu();
            menu.runMenu();
        } catch (Exception e) {
            System.out.println("Erro ao executar sistema: " + e.getMessage());
        }
    }
}
