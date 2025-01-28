import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    public static Scanner sc = new Scanner(System.in);
    public static List<User> users = new ArrayList<>();

    protected User signUp() {
        User user = new User();

        System.out.println("Creating an account:");
        System.out.print("First name: ");
        user.firstName = sc.nextLine();

        System.out.print("Last name: ");
        user.lastName = sc.nextLine();

        System.out.print("Email: ");
        user.email = sc.nextLine();

        System.out.print("Password: ");
        user.password = sc.nextLine();

        /*System.out.print("CPF: ");
        user.CPF = sc.nextLine();

        System.out.print("Date of birth: ");
        user.dateBirth = sc.nextLine();

        System.out.print("Phone Number: ");
        user.phoneNumber = sc.nextLine();*/

        /*System.out.println("Choose the type of account: ");
        System.out.println("1 - Checking account");
        System.out.println("2 - Saving account");
        int optionAccount = Integer.parseInt(sc.nextLine());
        String typeAccount = (optionAccount == 1 ? "Checking account" : "Saving account");*/

        System.out.println("Account details: ");
        System.out.println("• Name: " + user.firstName.substring(0, 1).toUpperCase() + user.firstName.substring(1) + " "
                + user.lastName.substring(0, 1).toUpperCase() + user.lastName.substring(1));
        System.out.println("• Email: " + user.email);
        System.out.println("• Password: " + user.password);

        /*System.out.println("• CPF: " + user.CPF);
        System.out.println("• Date of Birth: " + user.dateBirth);
        System.out.println("• Phone Number " + user.phoneNumber);
        System.out.println("• Type account: " + typeAccount);*/

        System.out.println("Are the account details correct (Y/N)?");
        String optionAccountDetails = sc.nextLine();

        if (optionAccountDetails.equalsIgnoreCase("y")) {
            System.out.println("Account created successfully!");
            users.add(user);
            return user;
        } else {
            System.out.println("Restarting the sign-up process...");
            return signUp();
        }
    }

    protected User signIn() {
        System.out.println("Enter the account information: ");

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();


        // precisei de ajuda do ChatGPT, nao estava conseguindo resolver o problema de checar cada email e cada senha de cada usuario para poder fazer login.
        User foundUser = users.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (foundUser != null) {
            System.out.println("Login successful!");
            return foundUser;
        } else {
            System.out.println("Invalid email or password.");
            return null;
        }
    }

    protected void accountLoggedIn(User user) {
        System.out.println("What do you want to do now?");
        System.out.println("1 - DEPOSIT");
        System.out.println("2 - WITHDRAW");
        System.out.println("3 - TRANSFER");
        System.out.println("4 - LOG OUT");
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1 -> user.balance = deposit(user);
            case 2 -> user.balance = withdraw(user);
            case 3 -> transfer(user);
            case 4 -> System.out.println("Logged out.");
            default -> System.out.println("Invalid option. Choose between 1 and 4.");
        }
    }

    private BigDecimal deposit(User user) {
        System.out.print ("How much do you want to deposit? R$");
        BigDecimal value = sc.nextBigDecimal();
        sc.nextLine();

        return user.balance.add(value);
    }

    private BigDecimal withdraw(User user) {
        System.out.println("How much do you want to withdraw? R$");
        BigDecimal value = sc.nextBigDecimal();
        sc.nextLine();

        if (user.balance.compareTo(value) < 0) {
            System.out.println("Insufficient balance.");
            return user.balance;
        }

        return user.balance.subtract(value);
    }

    public void transfer(User user) {
        System.out.print("Enter the email of the user you want to transfer to: ");
        String recipientEmail = sc.nextLine();

        // Ajuda do ChatGPT
        User recipient = users.stream()
                .filter(u -> u.getEmail().equals(recipientEmail))
                .findFirst()
                .orElse(null);

        if (recipient == null){
            System.out.println("User not found");
            return;
        }

        System.out.println("Enter the amount to transfer:");
        BigDecimal amount = sc.nextBigDecimal();

        if (user.balance.compareTo(amount) < 0) {
            System.out.println("Insufficient balance.");
            return;
        }

        user.balance = user.balance.subtract(amount);
        recipient.balance = recipient.balance.add(amount);

        System.out.println("Transfer completed successfully!");
    }
}