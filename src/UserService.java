import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    public static int count = 3;
    public static Scanner sc = new Scanner(System.in);
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
                    case 1 -> user.balance = deposit(user);
                    case 2 -> user.balance = withdraw(user);
                    case 3 -> transfer(user);
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

    protected BigDecimal deposit(User user) {
        boolean isRunning = true;
        do {
            System.out.print("How much do you want to deposit? R$");
            try {
                BigDecimal value = sc.nextBigDecimal();
                sc.nextLine();
                isRunning = false;
                return user.balance.add(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid value.");
            }
        } while (isRunning);

        return user.balance;
    }

    private BigDecimal withdraw(User user) {
        if (user.balance.compareTo(new BigDecimal(0)) == 0) {
            System.out.println("Insufficient balance.");
            return user.balance;
        }

        boolean isRunning = true;
        BigDecimal value = null;

        do {
            System.out.print("How much do you want to withdraw? R$");
            try {
                value = sc.nextBigDecimal();
                sc.nextLine();
                isRunning = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value.");
            }
        } while (isRunning);

        if (user.balance.compareTo(value) < 0) {
            System.out.println("Insufficient balance.");
            return user.balance;
        }

        if (user.typeAccount == UserTypeAccount.SALARY) {
            if (count <= 0) {
                System.out.println("You have already withdrawn 3 times in a month.");
                return user.balance;
            }

            System.out.println("You can withdraw " + count + " times in this month.");
            --count;

            System.out.println("Your withdrawal was successful.");
            System.out.println("Thank you for choosing our service!");

            user.balance = user.balance.subtract(value);
            return user.balance;
        }

        System.out.println("Your withdrawal was successful.");
        System.out.println("Thank you for choosing our service!");
        user.balance = user.balance.subtract(value);
        return user.balance;
    }

    public void transfer(User user) {
        if (user.balance.compareTo(new BigDecimal(0)) == 0) {
            System.out.println("Insufficient balance.");
            return;
        }

        System.out.print("Enter the email of the user you want to transfer to: ");
        String recipientEmail = sc.nextLine();

        // Ajuda do ChatGPT
        User recipient = users.stream()
                .filter(u -> u.getEmail().equals(recipientEmail))
                .findFirst()
                .orElse(null);

        if (recipient == null) {
            System.out.println("User not found");
            return;
        }

        System.out.print("Enter the amount to transfer: R$");
        BigDecimal amount = sc.nextBigDecimal();

        if (user.balance.compareTo(amount) < 0) {
            System.out.println("Insufficient balance.");
            return;
        }

        if (user.typeAccount == UserTypeAccount.SAVINGS) {
            if (amount.compareTo(new BigDecimal("50.0")) < 50.0) {
                System.out.println("Transfers below 50 are not allowed.");
            } else {
                user.balance = user.balance.subtract(amount);
                recipient.balance = recipient.balance.add(amount);
                System.out.println("Transfer completed successfully!");
            }
        } else {
            user.balance = user.balance.subtract(amount);
            recipient.balance = recipient.balance.add(amount);

            System.out.println("Transfer completed successfully!");
        }
    }
}