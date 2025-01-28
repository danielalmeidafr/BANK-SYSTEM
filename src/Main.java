import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static UserManager userManager = new UserManager();

    public static void main(String[] args) {
        User loggedInUser = null;
        int signInOrSignUp = 0;

        System.out.println("------------ BANK ACCOUNT ------------");
        do {
            if (loggedInUser == null) {
                System.out.println("Choose one option below: ");
                System.out.println("1 - SIGN UP");
                System.out.println("2 - SIGN IN");
                System.out.println("3 - CLOSE APP");
                signInOrSignUp = Integer.parseInt(sc.nextLine());

                switch (signInOrSignUp) {
                    case 1 -> loggedInUser = userManager.signUp();
                    case 2 -> loggedInUser = userManager.signIn();
                    case 3 -> System.out.println("Closing application...");
                    default -> System.out.println("Invalid option. Choose between 1 and 3");
                }
            } else {
                System.out.println("Welcome, " + loggedInUser.firstName + "!");
                userManager.accountLoggedIn(loggedInUser);
            }
        } while (signInOrSignUp != 3);
    }
}
