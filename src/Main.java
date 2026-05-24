import academy.investmais.model.investments.*;
import academy.investmais.ui.Menu;

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
