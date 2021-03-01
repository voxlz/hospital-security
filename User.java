import Authentication.Role;

public class User {

	private static int idCounter = 0;
	public final Role role;
	public final int id;
	String division;
	private String userName;
	private String password;

	public User(Role userType) {
		this.id = idCounter;
		idCounter++;
		this.role = userType;
	}

	public User(Role userType, String division) {
		this.id = idCounter;
		idCounter++;
		this.role = userType;
		this.division = division;
	}

	public int getId() {
		return id;
	}

	public Role getUserType() {
		return role;
	}

	public String getDivision() {
		if (role == Role.Nurse || role == Role.Doctor) {
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
		return (id + ", " + role.name() + ", " + division + ", " + userName + ", " + password);
	}

	public static User fromString(String str) {
		return null;
		// return this(str.split(", ")[]);
	}

}