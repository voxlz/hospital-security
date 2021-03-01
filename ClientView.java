
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class ClientView {
	PrintWriter out;
	BufferedReader in;
	Scanner read;
	String username;

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

		Scanner read = new Scanner(System.in, Charset.forName("UTF-8"));

		boolean isValidUser = false;

		while (!isValidUser) {
			System.out.print("Username: ");
			username = read.nextLine();

			System.out.print("Password: ");
			//String password = read.nextLine(); //för att det ska funka för Lisa
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

		String com = read.nextLine();
		userCommand(out, in, username, com);

		out.close();
		in.close();
	}

	private static void userCommand(PrintWriter out, BufferedReader in, String username, String command) {
		String msg = "c:" + username + "," + command;
		// want to check that it is a command

		out.print(msg);
		out.flush();

		String[] commandline = msg.split(",");
		command = commandline[1];
		System.out.println(command);
		out.println(msg);
		out.flush();
	}

	private static boolean authUserOnServer(PrintWriter out, BufferedReader in, String username, String password)
			throws IOException {
		String msg = "l:" + username + ',' + password;
		out.println(msg);
		out.flush();

		Boolean success = false;

		String res = in.readLine();

		System.out.println("server res: " + res);

		if (!res.isEmpty()) {
			// We succeded
			success = true;
		}
		return success;

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
