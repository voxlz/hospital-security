import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WriterReader {
  public ArrayList<String> readFile(String fileName) throws FileNotFoundException {
      File myFile = new File(fileName);
      Scanner myReader = new Scanner(myFile);
      int counter = 0;
      ArrayList<String> entries = new ArrayList<String>();
      while(myReader.hasNextLine()) {
        String data = myReader.nextLine();
        entries.add(data);
        counter += 1;
      }
      myReader.close();
      return entries;
  }

  public void createFile(String fileName, String id, String doctor, String nurse, String patient, String department) throws IOException {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
      writer.write(id + ", " + doctor + ", " + nurse + ", " + patient + ", " + department);
      writer.close();
  }
  
  public void deleteFile(String fileName, String id) throws IOException, FileNotFoundException {
    File myFile = new File(fileName);
    Scanner myReader = new Scanner(myFile);
    StringBuilder sb = new StringBuilder();
      while(myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if(!data.startsWith(id + ",")) {
          sb.append(data + "\n");
        }
      }
      myReader.close();
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
      writer.write(sb.toString());
      writer.close();
   }
}
