import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static UserService userService = new UserService();

    public static void main(String[] args) {
        System.out.println("------------ BANK ACCOUNT ------------");

        int signInOrSignUp;
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("Choose one option below: ");
                System.out.println("1 - SIGN UP");
                System.out.println("2 - SIGN IN");
                System.out.println("3 - CLOSE APP");
                signInOrSignUp = Integer.parseInt(sc.nextLine());

                switch (signInOrSignUp) {
                    case 1 -> userService.signUp();
                    case 2 -> userService.signIn();
                    case 3 -> {
                        System.out.println("Closing application...");
                        isRunning = false;
                    }
                    default -> System.out.println("Invalid option. Choose between 1 and 3");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");

            }
        }
    }
}
