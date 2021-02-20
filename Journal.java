package project2;

public class Journal{
	private User doctor;
	private User nurse;
	private User patient;
	private String division;
	
	public Journal(User doctor, User nurse, User Patient){
		this.doctor = doctor;
		this.nurse = nurse;
		this.patient = patient;
		this.division = division;
	}
	
	public boolean canRead(User user){
		if(user.equals(doctor) || user.equals(nurse) || user.equals(patient)){
			return true;
		}
		if(user.getUserType() == UserType.GOVERNMENT){
			return true;
		}
		return false;
	}
}
