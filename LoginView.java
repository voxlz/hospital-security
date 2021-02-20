import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class LoginView {
	public static void main(String[] args) {
		System.out.println("--------------------");
		System.out.println("Welcome to this hospital Security");
		System.out.println("--------------------");
		System.out.println("");
		
		User currentUser = validateUser();
		
		List<Journal> journals = printAvailableJournals();
		
		printOptions(currentUser, journals);
    
	}

    private static User validateUser(){
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
		User currentUser;
		//set current user
		in.close();
		return currentUser;
	}
	
	private static void printOptions(User currentUser, List<Journals> journals){
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
		
		switch (commands[0]){
			case "read":
				if(journals.get(commands[1])
				
				break;
			case "write":
				
				break;
			case "delete":
				
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
	
	private Journal getJournal(List<Journal> journals, String journalId){
		for (Journal j : journals){
			if(j.getId
		}
	}
	
	private static ArrayList<Journal> printAvailableJournals(){
		//do stuff
		return null;
	}
	private static boolean authUserOnServer(String username, String password) {
		return false;
	}
}
