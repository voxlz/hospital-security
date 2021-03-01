import Authentication.Role;

public class User {

	private static int idCounter = 0;
	private final Role userType;
	private final int id;
	private String division;
	private String userName;
	private String password;


	public User(UserType userType) {
		this.id = idCounter;
		idCounter++;
		this.userType = userType;
	}

	public User(UserType userType, String division) {
		this.id = idCounter;
		idCounter++;
		this.userType = userType;
		this.division = division;
	}

	public int getId() {
		return id;
	}

	public Role getUserType() {
		return userType;
	}

	public String getDivision() {
		if (userType == Role.Nurse || userType == Role.Doctor) {
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
	public String toString() {
		return (id + ", " + userType.name() + ", " + division + ", " + userName + ", " + password);
	}

	public static User fromString(String str) {
		return null;
		// return this(str.split(", ")[]);
	}

}