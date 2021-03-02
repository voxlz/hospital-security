import java.security.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Hash {
  private static byte[] generateSalt() {
    //A 16 byte salt or 24 character long String is generated
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  public static String hashPassword(String password) throws NoSuchAlgorithmException {
    byte[] salt = generateSalt();
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.reset();
    md.update(salt);
    byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
    StringBuffer hexString = new StringBuffer();
    hexString.append(Base64.getEncoder().encodeToString(salt));
    hexString.append(Base64.getEncoder().encodeToString(hashedPassword));
    return hexString.toString();
  }

  public static boolean verifyPassword(String hashPassword, String plainPassword) throws NoSuchAlgorithmException {
    boolean isVerified = false;
    String salt = hashPassword.substring(0, 24);
    byte[] byteSalt = Base64.getDecoder().decode(salt);
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.reset();
    md.update(byteSalt);
    byte[] hashedPassword = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
    StringBuffer hexString = new StringBuffer();
    hexString.append(Base64.getEncoder().encodeToString(byteSalt));
    hexString.append(Base64.getEncoder().encodeToString(hashedPassword));
    hexString.toString();
    isVerified = hashPassword.equals(hexString.toString());
    return isVerified;
  }
}
