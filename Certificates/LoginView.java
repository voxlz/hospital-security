import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class LoginView {
  public static void login(SSLSocket socket) {
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

      isValidUser = authUserOnServer(socket, username, password);

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

  private static boolean authUserOnServer(SSLSocket socket, String username, String password) {
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    out.write("l:" + username + ',' + password);
    String res = in.readLine();

    out.close();
    in.close();

    if (res == "ok")
      return true;
    else
      return false;
  }
}
