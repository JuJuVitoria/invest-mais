package academy.investmais.model.people;

import academy.investmais.enums.AccountStatus;
import academy.investmais.enums.AccountType;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private long accountID;
    private User primaryOwner;
    private AccountStatus accountStatus;
    private AccountType accountType;
    private List<User> users = new ArrayList<>();

    public Account(AccountType accountType, User primaryOwner) {
        this.accountType = accountType;
        this.primaryOwner = primaryOwner;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void addUser(User user) {
        if (this.accountType == AccountType.PERSONAL) {
            if (this.users.isEmpty()) {
                this.users.add(user);
            } else {
                System.out.println("Contas pessoais só podem ter um usuário.");
            }
        } else if (this.accountType == AccountType.BUSINESS) {
            this.users.add(user);
        }
    }

    public void setPrimaryOwner(User user) {
        this.primaryOwner = user;
    }

    public User getPrimaryOwner() {
        return primaryOwner;
    }

    public List<User> getUsers() {
        return this.users;
    }
}
