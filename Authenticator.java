
public class Authenticator {
  public static boolean allowAction(User user, Journal entry, Action action) {
    System.out.println(user.division);
    System.out.println(entry.division);
    System.out.println(user.division.equals(entry.division));
    System.out.println(user.toString() + " " + entry.toString());

    switch (action) {
      case read:
        if (user.id == entry.doctor || user.id == entry.nurse || user.id == entry.patient
            || user.division.equals(entry.division) || user.role == Role.Government)
          return true;
		break;
      case write:
        if (user.role == Role.Doctor || user.role == Role.Nurse) {
          if (user.id == entry.doctor || user.id == entry.nurse)
            return true;
        }
		break;
      case create:
        if (user.role == Role.Doctor && entry.doctor == user.id && user.division.equals(entry.division))
          return true;
		break;
      case delete:
        if (user.role == Role.Government)
          return true;
		break;
      default:
        break;
    }
    return false;
  }
}
