import java.util.Scanner;
import java.io.Console;

public class LoginView {
  public static void main(String[] args) {
    System.out.println("--------------------");
    System.out.println("Welcome to this hospital Security");
    System.out.println("--------------------");
    System.out.println("");

    Scanner in = new Scanner(System.in);

    System.out.print("Username: ");
    String username = in.nextLine();
    System.out.print("Password: ");
    String password = String.valueOf(System.console().readPassword());

    in.close();
  }
}
