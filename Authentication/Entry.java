package Authentication;

public class Entry {
  String department;
  User doctor;
  User nurse;
  User patient;

  public Entry(String department, User doctor, User nurse, User patient) {
    this.department = department;
    this.doctor = doctor;
    this.nurse = nurse;
    this.patient = patient;
  }

}
