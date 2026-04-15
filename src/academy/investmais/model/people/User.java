package academy.investmais.model.people;

import academy.investmais.enums.AccountType;

public class User extends Account {
    private long userID;
    private String name;
    private String email;
    private String password;

    public User(AccountType accountType, String name, String email) {
        super(accountType);
        this.name = name;
        this.email = email;
        super.setPrimaryOwner(this);
        super.addUser(this);
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

    @Override
    public String toString() {
        return this.name;
    }
}
