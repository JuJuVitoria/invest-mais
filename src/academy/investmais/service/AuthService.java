package academy.investmais.service;

import academy.investmais.model.people.User;
import academy.investmais.repository.UserRepository;

public class AuthService {
    private User loggedUser;
    private static UserRepository users = new UserRepository();

    public boolean login(String email, String password) {
        User user = users.findUserByEmail(email);

        if(user.getPassword().equals(password)) {
            this.loggedUser = user;
            return true;
        }

        return false;
    }

    public void logout() {
        this.loggedUser = null;
    }

    public boolean isAuthenticated() {
        return this.loggedUser != null;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
