package Authentication;

public class User {
  String id;
  Role role;
  String department;

  public User(String id, Role role, String department) {
    this.id = id;
    this.role = role;
    this.department = department;
  }
}
