package academy.investmais.model.people;

public class AccountTypeRecord {
    private int code;
    private String name;
    private int maxUsers;

    public AccountTypeRecord(int code, String name, int maxUsers) {
        this.code = code;
        this.name = name;
        this.maxUsers = maxUsers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }
}