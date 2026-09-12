package academy.investmais.enums;

public enum AccountStatus {
    ACTIVE(0), BLOCKED(1), PENDING(2);

    private final int code;

    AccountStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
