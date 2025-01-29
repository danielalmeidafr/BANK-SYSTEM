public enum UserTypeAccount {
    CHECKING("Checking"),
    SAVINGS("Saving"),
    SALARY("Salary");

    private final String typeAccount;

    UserTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getTypeAccount() {
        return typeAccount;
    }
}