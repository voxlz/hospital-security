import java.util.ArrayList;
import java.util.List;

public class User {

	private static int idCounter = 0;
	private final UserType userType;
	private final int id;
	private String division;
	private String userName;
	private String password;

	public User(UserType userType) {
		idCounter++;
		this.id = idCounter;
		this.userType = userType;
	}

	public User(UserType userType, String division) {
		idCounter++;
		this.id = idCounter;
		this.userType = userType;
		this.division = division;
	}

	public int getId() {
		return id;
	}

	public UserType getUserType() {
		return userType;
	}

	public String getDivision() {
		if (userType == UserType.NURSE || userType == UserType.DOCTOR) {
			return division;
		} else {
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		User u = (User) o;

		return (id == u.getId());
	}
	
	@Override
	public String toString(){
		return (userType + "," + id + "," + division + "," + userName + "," + password);
	}
}