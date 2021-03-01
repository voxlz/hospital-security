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
  
	public void createJournal(String fileName, Journal journal) throws IOException {
		write(fileName, journal.toString());
	}
	
	private void write(String fileName, final String s) throws IOException {
		Path filePath = Paths.get(fileName);
		Files.writeString(
			filePath,
			s + System.lineSeparator(),
			StandardOpenOption.CREATE,
			StandardOpenOption.APPEND
		);
	}
  
	public ArrayList<Journal> getJournals(String fileName) throws FileNotFoundException {
		Scanner input = new Scanner(new File(fileName));
		input.useDelimiter(",|\n");

		ArrayList<Journal> journals = new ArrayList<Journal>();
		while(input.hasNext()) {
			int doctor = input.nextInt();
			int nurse = input.nextInt();
			int patient = input.nextInt();
			String department = input.next();

			Journal newJournal = new Journal(doctor, nurse, patient, department);
			journals.add(newJournal);
		}
		
		return journals;
	}
	
	public ArrayList<User> getUsers(String fileName) throws FileNotFoundException {
		Scanner input = new Scanner(new File(fileName));
		input.useDelimiter(",|\n");

		ArrayList<User> users = new ArrayList<User>();
		while(input.hasNext()) {
			input.next();
			String role = input.next();
			String department = input.next();
			input.next();
			input.next();

			User newUser = new User(toUserType(role), department);
			users.add(newUser);
		}
		
		return users;
	}
	
	private UserType toUserType(String s){
		switch(s.toLowerCase()){
			case "doctor":	
				return UserType.DOCTOR;
			case "nurse":
				return UserType.DOCTOR;
			case "patient":
				return UserType.DOCTOR;
			case "government":
				return UserType.DOCTOR;
			default:
				return null;
		}
		
	}
}
