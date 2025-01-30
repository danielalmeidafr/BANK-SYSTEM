import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    public static Scanner sc = new Scanner(System.in);
    public static BankService bankService;
    public static List<User> users = new ArrayList<>();

    protected void signUp() {
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

        int optionAccount;
        while (true) {
            try {
                System.out.println("Choose the type of account: ");
                System.out.println("1 - Checking account");
                System.out.println("2 - Saving account");
                System.out.println("3 - Salary account");
                optionAccount = Integer.parseInt(sc.nextLine());

                if (optionAccount < 1 || optionAccount > 3) {
                    System.out.println("Error. Choose between 1 and 3.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }

        user.typeAccount = switch (optionAccount) {
            case 1 -> UserTypeAccount.CHECKING;
            case 2 -> UserTypeAccount.SAVINGS;
            case 3 -> UserTypeAccount.SALARY;
            default -> throw new IllegalStateException("Unexpected value: " + optionAccount);
        };

        System.out.println("Account details: ");
        System.out.println("• Name: " + user.firstName.substring(0, 1).toUpperCase() + user.firstName.substring(1) + " "
                + user.lastName.substring(0, 1).toUpperCase() + user.lastName.substring(1));
        System.out.println("• Email: " + user.email);
        System.out.println("• Password: " + user.password);
        System.out.println("• Type account: " + user.typeAccount.getTypeAccount());

        System.out.println("Are the account details correct (Y/N)?");
        String optionAccountDetails = sc.nextLine();

        if (optionAccountDetails.equalsIgnoreCase("y")) {
            System.out.println("Account created successfully!");
            users.add(user);
        } else {
            System.out.println("Restarting the sign-up process...");
            signUp();
        }
    }

    protected void signIn() {
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
            accountLoggedIn(foundUser);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void accountLoggedIn(User user) {
        System.out.println("Welcome, " + user.firstName.substring(0, 1).toUpperCase() + user.firstName.substring(1) + "!");

        int option;
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("Current balance: R$" + user.balance);
                System.out.println("What do you want to do now?");
                System.out.println("1 - DEPOSIT");
                System.out.println("2 - WITHDRAW");
                System.out.println("3 - TRANSFER");
                System.out.println("4 - LOG OUT");
                option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1 -> user.balance = bankService.deposit(user);
                    case 2 -> user.balance = bankService.withdraw(user);
                    case 3 -> bankService.transfer(user);
                    case 4 -> {
                        System.out.println("Logged out.");
                        isRunning = false;
                    }
                    default -> System.out.println("Invalid option. Choose between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }
        }
    }
}