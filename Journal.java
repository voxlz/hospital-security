import Authentication.Role;

public class Journal {

	private static int idCounter = 0;
	private int doctor;
	private int nurse;
	private int patient;
	private final int id;
	private String division;

	public Journal(int doctor, int nurse, int patient, String division) {
		id = idCounter;
		idCounter++;
		this.doctor = doctor;
		this.nurse = nurse;
		this.patient = patient;
		this.division = division;
	}

	public boolean canRead(User currentUser) {
		if (currentUser.equals(doctor) || currentUser.equals(nurse) || currentUser.equals(patient)) {
			return true;
		}
		if (currentUser.getUserType() == Role.Government) {
			return true;
		}
		return checkDivision(currentUser);
	}

	public boolean checkDivision(User user) {
		if (user.getDivision() == null) {
			return false;
		} else if (user.getDivision().equals(division)) {
			return true;
		}
		return false;
	}

	public boolean canWrite(User user) {
		if (user.equals(doctor) || user.equals(nurse)) {
			return true;
		}
		return false;
	}

	public boolean canDelete(User user) {
		if (user.getUserType() == Role.Government) {
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return (id + ", " + doctor + ", " + nurse + ", " + patient + ", " + division);
	}
}
