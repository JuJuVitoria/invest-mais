package academy.investmais.model.people;

import academy.investmais.enums.AccountType;

import java.util.UUID;

public class User {
    private String userID;
    private String accountID;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password, String accountID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = UUID.randomUUID().toString();
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() { return userID; }
}
