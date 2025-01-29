import java.math.BigDecimal;

public class User {
    protected String firstName;
    protected String lastName;
    protected UserTypeAccount typeAccount;
    protected String email;
    protected String password;
    protected BigDecimal balance;

    public User() {
        this.balance = BigDecimal.valueOf(0.0);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}