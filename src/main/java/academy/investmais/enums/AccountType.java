package academy.investmais.enums;

public enum AccountType {
    PERSONAL(0),
    BUSINESS(1);

    private final int code;

    AccountType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
