public class Journal {
	
	private static int idCounter = 0;
	private User doctor;
	private User nurse;
	private User patient;
	private final int id;
	private String division;

	public Journal(User doctor, User nurse, User patient, String division) {
		idCounter++;
		id = idCounter;
		this.doctor = doctor;
		this.nurse = nurse;
		this.patient = patient;
		this.division = division;
	}

	public boolean canRead(User user) {
		if (user.equals(doctor) || user.equals(nurse) || user.equals(patient)) {
			return true;
		}
		if (user.getUserType() == UserType.GOVERNMENT) {
			return true;
		}
		return checkDivision(user);
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
		if(user.getUserType() == UserType.GOVERNMENT){
			return true;
		}
		return false;
	}
	
	public int getId() {
		return id;
	}
}
