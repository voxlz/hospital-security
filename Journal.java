public class Journal {
	private User doctor;
	private User nurse;
	private User patient;
	private String division;

	public Journal(User doctor, User nurse, User patient, String division) {
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
<<<<<<< HEAD
		return checkDivision(user);
	}
	
	public boolean checkDivision(User user){
		if(user.getDivision() == null){
			return false;
		} else if(user.getDivision().equals(division)){
=======
		if (checkDivision(user)) {
			return checkDivision(user);
		}
		return false;
	}

	public boolean checkDivision(User user) {
		if (user.getDivision() == null) {
			return false;
		} else if (user.getDivision().equals(division)) {
>>>>>>> 4b54150d5715aeadb13e7904ee7ceedaac3077c2
			return true;
		}
		return false;
	}

	public boolean canWrite(User user) {
		if (user.equals(doctor)) {
			return true;
		}
		return false;
	}
}
