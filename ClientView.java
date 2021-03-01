
import Authentication.Role;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class ClientView {
	PrintWriter out;
	BufferedReader in;
	Scanner read;

	public ClientView(SSLSocket socket) throws IOException {
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		read = new Scanner(System.in);
	}

	public void login() throws IOException {
		System.out.println("--------------------");
		System.out.println("Welcome to this hospital Security");
		System.out.println("--------------------");
		System.out.println("");

		boolean isValidUser = false;

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
				// display all patients it has reed access to. Must se what role is has.
				// tänk vi är läkare vill ha info om patienter. be server om den infon
			}
		}
	}

	private ArrayList<User> patients(PrintWriter out, BufferedReader in, Role role) {
		// via connectionen med serven vill jag skicka ett request om att få info över
		// patienter.
		return null;
	}

	private static boolean authUserOnServer(PrintWriter out, BufferedReader in, String username, String password)
			throws IOException {
		String msg = "l:" + username + ',' + password;
		out.println(msg);
		out.flush();

		String res = in.readLine();

		System.out.println("server res: " + res);

		if (res.equals("ok"))
			return true;
		else
			return false;
	}

	private static void printOptions(User currentUser, List<Journal> journals) {
		Scanner read = new Scanner(System.in);
		String command = read.nextLine();

		System.out.println("");
		System.out.println("Please use one of the following options: ");
		System.out.println("read \"id\"");
		System.out.println("write \"id\"");
		System.out.println("delete \"id\"");
		System.out.println("create \"id\"");
		System.out.println("");

		String[] commands = command.split(" ");

		switch (commands[0]) {
			case "read":
				if (getJournal(journals, Integer.parseInt(commands[1])).canRead(currentUser)) {
					System.out.println("");
					System.out.println("Access granted");
					System.out.println("");
				}

				break;
			case "write":
				if (getJournal(journals, Integer.parseInt(commands[1])).canWrite(currentUser)) {
					System.out.println("");
					System.out.println("Access granted");
					System.out.println("");
				}
				break;
			case "delete":
				if (getJournal(journals, Integer.parseInt(commands[1])).canDelete(currentUser)) {
					System.out.println("");
					System.out.println("Access granted");
					System.out.println("");
				}
				break;
			case "create":

				break;
			default:
				System.out.println("");
				System.out.println("Please use one of the correct options!");
				System.out.println("");
		}

	}

	private static Journal getJournal(List<Journal> journals, int journalId) {
		for (Journal j : journals) {
			if (j.getId() == journalId) {
				return j;
			}
		}
		return null;
	}

	public void commandLoop() throws IOException {

		boolean quit = false;
		while (!quit) {
			// Reading data using readLine
			System.out.print("> ");

			String command = read.nextLine();

			if (command.equals("help")) {
				System.out.println("read \"id\"");
				System.out.println("write \"id\"");
				System.out.println("delete \"id\"");
				System.out.println("create \"id\"");
			} else if (command.equals("quit")) {
				quit = true;
			} else {
				System.out.println("Totally real server response to " + command + " ...");
			}
			System.out.println("");
		}
	}

	public void close() throws IOException {
		out.close();
		in.close();
		read.close();
	}
}
