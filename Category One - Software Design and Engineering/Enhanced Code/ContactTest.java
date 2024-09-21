package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Contact.Contact;

class ContactTest {
	private Contact contact;

	// ENHANCMENTS- Creating constants for test strings
	private static final String VALID_CONTACT_ID = "1";
	private static final String VALID_FIRST_NAME = "Noah";
	private static final String VALID_LAST_NAME = "Garza";
	private static final String VALID_PHONE = "8888888888";
	private static final String VALID_ADDRESS = "8 Penguin Dr, Mission TX, 7887";

	private static final String LONG_FIRST_NAME = "Iamenjoyingthisclassasitprovidesagoodamountofchallenge";
	private static final String LONG_LAST_NAME = "I updated the last name to be longer than 10 characters";
	private static final String SHORT_PHONE = "99999";
	private static final String LONG_PHONE = "99999999999";
	private static final String LONG_ADDRESS = "123456789012345678901234567890 30";

	// Creating contact objects before each test is run.
	@BeforeEach
	public void createContact() {
		contact = new Contact(VALID_CONTACT_ID, VALID_FIRST_NAME, VALID_LAST_NAME, VALID_PHONE, VALID_ADDRESS);
	}

	// Verifying object creation
	@Test
	void testContactClass() {
		assertTrue(contact.getContactId().equals(VALID_CONTACT_ID));
		assertTrue(contact.getFirstName().equals(VALID_FIRST_NAME));
		assertTrue(contact.getLastName().equals(VALID_LAST_NAME));
		assertTrue(contact.getPhone().equals(VALID_PHONE));
		assertTrue(contact.getAddress().equals(VALID_ADDRESS));
	}

	// Verifying if exception is caught if the first name is too long.
	@Test
	void testContactFirstNameTooLong() {
		assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(LONG_FIRST_NAME));
	}

	// UPDATED: TEST TO VERIFY THAT THE LAST NAME IS LESS THAN 10 CHARACTERS
	@Test
	void testContactLastNameTooLong() {
		assertThrows(IllegalArgumentException.class, () -> contact.setLastName(LONG_LAST_NAME));
	}

	// Verifying null check for firstName.
	@Test
	void testContactWithNoFirstName() {
		assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
	}

	// Verifying null check for lastName.
	@Test
	void testContactWithNoLastName() {
		assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
	}

	// Verifying null check for phone number.
	@Test
	void testContactWithNoPhone() {
		assertThrows(IllegalArgumentException.class, () -> contact.setPhone(null));
	}

	// UPDATED: TEST TO CHECK THAT PHONE MUST BE 10 CHARACTERS
	@Test
	void testPhoneLengthNotTen() {
		assertThrows(IllegalArgumentException.class, () -> contact.setPhone(SHORT_PHONE));
		assertThrows(IllegalArgumentException.class, () -> contact.setPhone(LONG_PHONE));
	}

	// Verifying null check for address.
	@Test
	void testContactWithNoAddress() {
		assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null));
	}

	// UPDATED: TEST TO CHECK IF ADDRESS IS LESS THAN 30 CHARACTERS
	@Test
	void testAddressLength() {
		assertThrows(IllegalArgumentException.class, () -> contact.setAddress(LONG_ADDRESS));
	}
}
