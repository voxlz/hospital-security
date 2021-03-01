import java.security.*;
import java.nio.charset.StandardCharsets;

public class Hash {
  private byte[] generateSalt() {
    //A 16 byte salt or 32 character in hexString is generated
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  public void hashPassword(String password) throws NoSuchAlgorithmException {
    byte[] salt = generateSalt();
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(salt);
    byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
    StringBuffer hexString = new StringBuffer();
     for (int i = 0; i < salt.length; i++) {
         hexString.append(Integer.toHexString(0xFF & salt[i]));
     }
     for (int i = 0; i < hashedPassword.length; i++) {
         hexString.append(Integer.toHexString(0xFF & hashedPassword[i]));
      }
    System.out.println(hexString.toString());
  }
}
