import Authentication.Role;
import Authentication.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class LoginView {
	public static void login(SSLSocket socket) throws IOException {
		System.out.println("--------------------");
		System.out.println("Welcome to this hospital Security");
		System.out.println("--------------------");
		System.out.println("");

		Scanner read = new Scanner(System.in);

		boolean isValidUser = false;

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		while (!isValidUser) {
			System.out.print("Username: ");
			String username = read.nextLine();
			System.out.print("Password: ");
			String password = String.valueOf(System.console().readPassword());

			isValidUser = authUserOnServer(out, in, username, password);

			if (!isValidUser) {
				System.out.println("");
				System.out.println("Authentication failed: No such user or password");
				System.out.println("");
			} else {
				System.out.println("");
				System.out.println("Authentication success! Logged in as " + username + "");
				System.out.println("");
				// request access too information- get a journal
				// display all patients it has reed access to. Must se what role is has.
				// tänk vi är läkare vill ha info om patienter. be server om den infon

			}
		}

		in.close();
	}

	private static boolean authUserOnServer(PrintWriter out, BufferedReader in, String username, String password)
			throws IOException {
		String msg = "l:" + username + ',' + password;
		out.println(msg);
		out.flush();

		String res = in.readLine();

		System.out.println("server res: " + res);

		out.close();
		in.close();

		if (res.equals("ok"))
			return true;
		else
			return false;
	}
}
