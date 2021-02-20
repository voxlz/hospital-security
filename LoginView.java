import java.util.Scanner;

public class LoginView {
  public static void main(String[] args) {
    System.out.println("--------------------");
    System.out.println("Welcome to this hospital Security");
    System.out.println("--------------------");
    System.out.println("");

    Scanner in = new Scanner(System.in);

    boolean isValidUser = false;
    while (!isValidUser) {
      System.out.print("Username: ");
      String username = in.nextLine();
      System.out.print("Password: ");
      String password = String.valueOf(System.console().readPassword());

      isValidUser = authUserOnServer(username, password);

      if (!isValidUser) {
        System.out.println("");
        System.out.println("Authentication failed: No such user or password");
        System.out.println("");
      } else {
        System.out.println("");
        System.out.println("Authentication success! Logged in as " + username + "");
        System.out.println("");
      }
    }

    in.close();
  }

  private static boolean authUserOnServer(String username, String password) {
    return false;
  }
}
