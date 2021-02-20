package Certificates;

import Authentication.Role;
import Authentication.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

		out.close();
		in.close();

		if (res.equals("ok"))
			return true;
		else
			return false;
	}

	private static void printOptions(User currentUser, List<Journal> journals) {
		Scanner in = new Scanner(System.in);

		System.out.println("");
		System.out.println("Please use one of the following options: ");
		System.out.println("read \"id\"");
		System.out.println("write \"id\"");
		System.out.println("delete \"id\"");
		System.out.println("create \"id\"");
		System.out.println("");

		String command = in.nextLine();
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

		in.close();
	}

	private static Journal getJournal(List<Journal> journals, int journalId) {
		for (Journal j : journals) {
			if (j.getId() == journalId) {
				return j;
			}
		}
		return null;
	}

	private static ArrayList<Journal> printAvailableJournals() {
		// do stuff
		return null;
	}
}
