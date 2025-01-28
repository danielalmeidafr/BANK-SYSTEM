import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static UserManager userManager = new UserManager();

    public static void main(String[] args) {

        int signInOrSignUp;
        System.out.println("------------ BANK ACCOUNT ------------");
        do {
            System.out.println("Choose one option below: ");
            System.out.println("1 - SIGN UP");
            System.out.println("2 - SIGN IN");
            System.out.println("3 - CLOSE APP");
            signInOrSignUp = Integer.parseInt(sc.nextLine());

            switch (signInOrSignUp) {
                case 1 -> userManager.signUp();
                case 2 -> userManager.signIn();
                case 3 -> {
                }
                default -> System.out.println("Invalid option. Choose between 1 and 2");
            }
        } while (signInOrSignUp != 3);

    }
}