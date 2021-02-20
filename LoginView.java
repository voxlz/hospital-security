import java.util.Scanner;

public class LoginView {
	public static void main(String[] args) {
		System.out.println("--------------------");
		System.out.println("Welcome to this hospital Security");
		System.out.println("--------------------");
		System.out.println("");
		
		validateUser();
		
		printOptions();
    
	}

    private static void validateUser(){
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
	
	private static void printOptions(){
		Scanner in = new Scanner(System.in);
		
		System.out.println("");
		System.out.println("Please use one of the following options: ");
		System.out.println("read");
		System.out.println("write");
		System.out.println("delete");
		System.out.println("create");
		System.out.println("");
		
		String command = in.nextLine();
		
		switch (command){
			case "read":
				printAvailableJournals();
				
				break;
			case "write":
				printAvailableJournals();
				
				break;
			case "delete":
				printAvailableJournals();
				
				break;
			case "create":
				printAvailableJournals();
				
				break;
			default:
				System.out.println("");
				System.out.println("Please use one of the correct options!");
				System.out.println("");
		}
	}
	
	private static void printAvailableJournals(){
		//do stuff
	}
	private static boolean authUserOnServer(String username, String password) {
		return false;
	}
}
