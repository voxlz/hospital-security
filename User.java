
public class User {

	private final UserType userType;
	private final String id;
	private String division;

	public User(String id, UserType userType) {
		this.id = id;
		this.userType = userType;
	}

	public User(String id, UserType userType, String division) {
		this.id = id;
		this.userType = userType;
		this.division = division;
	}

	public String getId() {
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

		return id.equals(u.getId());
	}
}