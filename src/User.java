import java.math.BigDecimal;

public class User {
    protected String firstName;
    protected String lastName;
    /*protected String CPF;
    protected String dateBirth;
    protected String phoneNumber;*/
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