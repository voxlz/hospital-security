import java.security.*;
import java.nio.charset.StandardCharsets;

public class Hash {

  public void hashPassword(String password) throws NoSuchAlgorithmException {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(salt);
    byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
    StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < hashedPassword.length; i++) {
         hexString.append(Integer.toHexString(0xFF & hashedPassword[i]));
      }
    System.out.println(hexString.toString());
  }
}
