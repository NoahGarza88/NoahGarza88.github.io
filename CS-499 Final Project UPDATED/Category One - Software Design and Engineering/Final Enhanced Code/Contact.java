package Contact;

public class Contact {
	private String contactId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	private String group; // Added grouping attribute

	// Constants for validation
	private static final int MAX_FIRST_NAME_LENGTH = 10;
	private static final int MAX_LAST_NAME_LENGTH = 10;
	private static final int PHONE_LENGTH = 10;
	private static final int MAX_ADDRESS_LENGTH = 30;

	public Contact(String contactId, String firstName, String lastName, String phone, String address, String group) {
		setContactId(contactId);
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setAddress(address);
		setGroup(group); // Initialize group
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() > MAX_FIRST_NAME_LENGTH) {
			throw new IllegalArgumentException(
					"Error: First name must not be null and cannot exceed " + MAX_FIRST_NAME_LENGTH + " characters.");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() > MAX_LAST_NAME_LENGTH) {
			throw new IllegalArgumentException(
					"Error: Last name must not be null and cannot exceed " + MAX_LAST_NAME_LENGTH + " characters.");
		}
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone == null || phone.length() != PHONE_LENGTH) {
			throw new IllegalArgumentException(
					"Error: Phone number must not be null and must be exactly " + PHONE_LENGTH + " digits.");
		}
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address == null || address.length() > MAX_ADDRESS_LENGTH) {
			throw new IllegalArgumentException(
					"Error: Address must not be null and cannot exceed " + MAX_ADDRESS_LENGTH + " characters.");
		}
		this.address = address;
	}

	// -----------FINAL ENHANCMENT BASED ON FEEDBACK (setGroup)--------------------
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group; // Allow grouping of contacts
	}
}
