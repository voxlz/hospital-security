
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class ClientView {
	PrintWriter out;
	BufferedReader in;
	Scanner read;
	String username;
	private boolean isCommand;
	private Action action;

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
			//String password = read.nextLine();
			String password = String.valueOf(System.console().readPassword());

			isValidUser = authUserOnServer(username, password);
		}
	}

	private void userCommand(String username, String command) throws IOException {
		String msg = "c:" + command;
		// want to check that it is a command

		// out.print(msg);
		// out.flush();

		// String[] commandline = msg.split(",");
		// command = commandline[1];
		// System.out.println(command);
		out.println(msg);
		out.flush();

		String res = in.readLine();
		System.out.println(res);
	}

	private boolean authUserOnServer(String username, String password) throws IOException {
		String msg = "l:" + username + ',' + password;
		out.println(msg);
		out.flush();

		Boolean success = false;

		String res = in.readLine();

		if (!res.isEmpty()) {
			System.out.println("");
			System.out.println("Authentication success! Logged in as " + username + "");
			System.out.println("");

			System.out.println(
					"Your journal: \n======================================\nid\tdoctor\tnurse\tpatient\tdepart\n------------------------------------\n"
							+ res.substring(3).replaceAll(", ", "\t").replaceAll(";", "\n"));

			// System.out.println(res);

			// We succeeded
			success = true;
		} else {
			System.out.println("");
			System.out.println("Authentication failed: No such user or password");
			System.out.println("");
		}
		return success;
	}

	// private static Journal getJournal(List<Journal> journals, int journalId) {
	// for (Journal j : journals) {
	// if (j.getId() == journalId) {
	// return j;
	// }
	// }
	// return null;
	// }

	public void commandLoop() throws IOException {
		boolean quit = false;
		while (!quit) {
			// Reading data using readLine
			System.out.print("> ");

			String command = read.nextLine();

			try {
				action = Action.valueOf(command.split(" ")[0]);
				isCommand = Arrays.asList(Action.values()).contains(action);
			} catch (Exception e) {
				isCommand = false;
			}

			// System.out.println(action + " " + isCommand);

			if (command.equals("help")) {
				System.out.println("read \"id\"");
				System.out.println("write \"id\"");
				System.out.println("delete \"id\"");
				System.out.println("create \"id\"");
			} else if (command.equals("quit")) {
				String msg = "q:" + command;
				out.println(msg);
				out.flush();
				quit = true;
			} else {
				if (isCommand) {
					if (command.split(" ").length < 2) {
						System.out.println("Please write an id...");
					} else {
						userCommand(username, command);
					}
				} else {
					System.out.println(command + "does not exist...");
				}
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
