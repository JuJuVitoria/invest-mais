package academy.investmais.ui.processes;

import academy.investmais.repository.UserRepository;
import academy.investmais.service.AuthService;

import java.util.Scanner;

public class MenuLoginProcess {
    private Scanner scanner = new Scanner(System.in);
    private AuthService auth;

    public MenuLoginProcess(AuthService auth) {
        this.auth = auth;
    }

    public void menuLogin() {
        System.out.println("--------------- LOGIN ---------------");
        System.out.println("Email: ");
        String email = scanner.next();

        System.out.println("Senha: ");
        String password = scanner.next();

        if(auth.login(email, password)) {
            System.out.println("Login efetuado com sucesso!");
        } else {
            System.out.println("Algo deu errado!");
        }
    }
}
