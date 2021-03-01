import java.io.*;
import java.util.Scanner;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class WriterReader {
  public static ArrayList<String> readFile(String fileName) throws FileNotFoundException {
    File myFile = new File(fileName);
    Scanner myReader = new Scanner(myFile);
    int counter = 0;
    ArrayList<String> entries = new ArrayList<String>();
    while (myReader.hasNextLine()) {
      String data = myReader.nextLine();
      entries.add(data);
      counter += 1;
    }
    myReader.close();
    return entries;
  }

  public static void createFile(String fileName, String id, String doctor, String nurse, String patient,
      String department) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
    writer.write(id + ", " + doctor + ", " + nurse + ", " + patient + ", " + department);
    writer.close();
  }

  public static void deleteFile(String fileName, String id) throws IOException, FileNotFoundException {
    File myFile = new File(fileName);
    Scanner myReader = new Scanner(myFile);
    StringBuilder sb = new StringBuilder();
    while (myReader.hasNextLine()) {
      String data = myReader.nextLine();
      if (!data.startsWith(id + ",")) {
        sb.append(data + "\n");
      }
    }
    myReader.close();
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
    writer.write(sb.toString());
    writer.close();
  }
	
	/* Takes a fileName and a Journal and calls write()
	 * Throws something if something going wrong
	 */
	public static void createJournal(String fileName, Journal journal) throws IOException {
		write(fileName, journal.toString());
	}
	
	/* Takes a fileName and a User and calls write()
	 * Throws something if something going wrong
	 */
	public static void createUser(String fileName, User user) throws IOException {
		write(fileName, user.toString());
	}
	
	/* Takes a filename and a String and adds that string in a newline to the file
	 * Creates a new file if it doesnt exist
	 */
	private static void write(String fileName, final String s) throws IOException {
		Path filePath = Paths.get(fileName);
		Files.writeString(filePath, System.lineSeparator() + s, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}
	
	/* Returns a list of all the Journals that are in fileName
	 * program crashes if fileName has worng format
	 * Throws file not found exception when file is not found
	 */
	public static ArrayList<Journal> getJournals(String fileName) throws FileNotFoundException {
		Scanner input = new Scanner(new File(fileName));
		input.useDelimiter(", |\n");

		ArrayList<Journal> journals = new ArrayList<Journal>();

		for (int i = 0; i < 5; i++) {
			input.next();
		}

		while (input.hasNext()) {
			input.next();
			String doctor = input.next();
			String nurse = input.next();
			String patient = input.next();
			String department = input.next();

			Journal newJournal = new Journal(Integer.parseInt(doctor), Integer.parseInt(nurse), Integer.parseInt(patient),
					department);
			journals.add(newJournal);
		}
		input.close();
		return journals;
	}
	
	/* Returns a list of all the users that are in fileName
	 * program crashes if fileName has worng format
	 * Throws file not found exception when file is not found
	 */
	public static ArrayList<User> getUsers(String fileName) throws FileNotFoundException {
		Scanner input = new Scanner(new File(fileName));
		input.useDelimiter(", |\n");

		ArrayList<User> users = new ArrayList<User>();

		for (int i = 0; i < 5; i++) {
			input.next();
		}

		while (input.hasNext()) {
			input.next();
			String role = input.next();
			String department = input.next();
			input.next();
			input.next();

			User newUser = new User(Role.valueOf(role), department);
			users.add(newUser);
		}
		input.close();
		return users;
	}

	/* Converts String to enum
	 * Should have used built in function, ops
	 */
	private static UserType toUserType(String s){
		switch(s.toLowerCase()){
			case "doctor":	
				return UserType.DOCTOR;
			case "nurse":
				return UserType.NURSE;
			case "patient":
				return UserType.PATIENT;
			case "government":
				return UserType.GOVERNMENT;
			default:
				return null;
		}

	}
}
