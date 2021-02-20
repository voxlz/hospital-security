package Authentication;

public class Authenticator {
  public static boolean allowAction(User user, Entry entry, Action action) {
    switch (action) {
      case Read:
        if (user.id == entry.doctor.id || user.id == entry.nurse.id || user.id == entry.patient.id
            || user.department == entry.department || user.role == Role.Government)
          return true;
      case Write:
        if (user.role == Role.Doctor || user.role == Role.Nurse) {
          if (user.id == entry.doctor.id || user.id == entry.nurse.id)
            return true;
        }
      case Create:
        if (user.role == Role.Doctor && entry.doctor.id == user.id && entry.department == user.department)
          return true;
      case Delete:
        if (user.role == Role.Government)
          return true;
      default:
        break;
    }
    return false;
  }
}
