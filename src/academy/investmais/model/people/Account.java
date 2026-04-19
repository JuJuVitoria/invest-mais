package academy.investmais.model.people;

import academy.investmais.enums.AccountStatus;
import academy.investmais.enums.AccountType;
import academy.investmais.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {
    private String accountID;
    private String iDprimaryOwner;
    private AccountStatus accountStatus;
    private AccountType accountType;
    private List<User> users = new ArrayList<>();

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.accountID = UUID.randomUUID().toString();
    }

    public AccountType getAccountType() {
        return this.accountType;
    }
    public String getAccountID() { return accountID; }

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

    public void setiDPrimaryOwner(String userID) {
        this.iDprimaryOwner = userID;
    }

    public String getiDPrimaryOwner() {
        return iDprimaryOwner;
    }

    public List<User> getUsers() {
        return this.users;
    }
}
