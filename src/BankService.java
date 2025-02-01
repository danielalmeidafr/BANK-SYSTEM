import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankService {
    public static int count = 3;
    public static Scanner sc = new Scanner(System.in);
    public static List<User> users = new ArrayList<>();

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

    protected BigDecimal withdraw(User user) {
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

    protected void transfer(User user) {
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
