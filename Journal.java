
public class Journal {

	//private static int idCounter = 0;
	public final int doctor;
	public final int nurse;
	public final int patient;
	private final int id;
	final String division;

	public Journal(int id, int doctor, int nurse, int patient, String division) {
		//idCounter++;
		this.id = id;
		this.doctor = doctor;
		this.nurse = nurse;
		this.patient = patient;
		this.division = division.trim();
	}

	public boolean canRead(User currentUser) {
		if (currentUser.id == doctor || currentUser.equals(nurse) || currentUser.equals(patient)) {
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

	public int getPatient() {
		return patient;
	}

	@Override
	public String toString() {
		return (id + ", " + doctor + ", " + nurse + ", " + patient + ", " + division.trim()).trim().replaceAll("\n", "");
	}
}
