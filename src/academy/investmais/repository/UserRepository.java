package academy.investmais.repository;

import academy.investmais.model.people.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static List<User> usersDatabase = new ArrayList<>();

    public List<User> getUsersDatabase() {
        return usersDatabase;
    }

    public void addUserOnDatabase(User user) {
        usersDatabase.add(user);
    }
}
