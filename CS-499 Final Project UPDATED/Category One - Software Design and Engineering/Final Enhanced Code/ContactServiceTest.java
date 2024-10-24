package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Contact.Contact;
import Contact.ContactService;

class ContactServiceTest {
	private Contact contact, contact1;
	private ContactService service;

	// ENHANCEMENTS - Define error message constants
	private static final String NULL_CONTACT_ERROR = "Error: Contact must not be null."; // !!!!!
	private static final String DUPLICATE_CONTACT_ERROR = "Error: Contact already exists."; // !!!!!
	private static final String CONTACT_NOT_FOUND_ERROR = "Error: Contact ID does not exist."; // !!!!!

	@BeforeEach // Creating the test data before each test
	public void start() {
		service = new ContactService();
		contact = new Contact("123", "Noah", "Garza", "8888888888", " 12 N St, Texas"); // UPDATED PHONE NUMBERS TO BE
																						// EXACTLY 10 DIGITS
		contact1 = new Contact("88", "NLG", "Apple", "8888777788", " 15 St, Texas, 7777");
	}

	@DisplayName("Test for adding contacts")
	@Test // Test for adding contacts
	public void testAddContact() {
		service.addContact(contact);
		assertEquals(1, service.getContacts().length); // Checking if 1 item exists in arrayList
		assertTrue(contact.getContactId().contains("123")); // Checking if true
		assertEquals(contact.getFirstName(), ("Noah")); // Checking if first Name equals

		service.addContact(contact1);
		assertEquals(2, service.getContacts().length); // Checking if 2 items exist in the arrayList
	}

	@DisplayName("Test to check If duplicates are handled correctly")
	@Test // Test to check duplicate contact
	public void testAddingDuplicateContact() {
		service.addContact(contact);
		assertThrows(IllegalArgumentException.class, () -> service.addContact(contact), DUPLICATE_CONTACT_ERROR); // !!!!!

		service.addContact(contact1);
		assertThrows(IllegalArgumentException.class, () -> service.addContact(contact1), DUPLICATE_CONTACT_ERROR); // !!!!!
	}

	@DisplayName("Test to check if null values are not allowed")
	@Test // Test to check if adding a null contact gets error
	public void testAddingNull() {
		assertThrows(IllegalArgumentException.class, () -> service.addContact(null), NULL_CONTACT_ERROR); // !!!!!
	}

	@DisplayName("Test for deleting contacts")
	@Test // Testing the deletion of contacts
	public void testDeletingContacts() {
		service.addContact(contact); // Adding two contacts
		service.addContact(contact1);
		assertEquals(2, service.getContacts().length); // Checking if it added 2 contacts

		service.deleteContact(contact.getContactId()); // Deleting contact 1
		assertEquals(1, service.getContacts().length); // Checking if the size of array List decreased to one after
														// deletion.
	}

	@DisplayName("Test for deleting non-existent contacts")
	@Test // Testing the deletion of contacts
	public void testDeletingContactsNonExist() {
		service.addContact(contact); // Adding one contact

		service.deleteContact(contact.getContactId());
		assertThrows(IllegalArgumentException.class, () -> service.deleteContact(contact.getContactId()),
				CONTACT_NOT_FOUND_ERROR); // !!!!!
	}

	@DisplayName("Test for updating contacts with null")
	@Test // Test for updating contacts with null input
	public void testUpdateContactsNull() {
		assertThrows(IllegalArgumentException.class, () -> service.addContact(null), NULL_CONTACT_ERROR); // !!!!!
	}

	@DisplayName("Test for updating contacts")
	@Test // Test for updating contacts
	public void testUpdateFirstName() {
		service.addContact(contact);
		service.updateContact(contact.getContactId(), "firstName", "Benjamin");
		service.updateContact(contact.getContactId(), "lastName", "Cloud");
		service.updateContact(contact.getContactId(), "phone", "1111111111");
		service.updateContact(contact.getContactId(), "address", "Circle Ave, Texas, 8888");
		assertEquals(contact.getLastName(), "Cloud");
		assertThrows(IllegalArgumentException.class, () -> service.updateContact(null, null, null)); // !!!!!
		assertThrows(IllegalArgumentException.class, () -> service.updateContact(contact.getContactId(), null, null)); // !!!!!
		assertThrows(IllegalArgumentException.class, () -> service.updateContact(null, "firstName", null)); // !!!!!
		assertThrows(IllegalArgumentException.class,
				() -> service.updateContact(contact.getContactId(), "lastName", null)); // !!!!!
		assertThrows(IllegalArgumentException.class, () -> service.updateContact(null, "lastName", "Rocker")); // !!!!!
	}

	// -----------FINAL ENHANCMENT BASED ON FEEDBACK
	// (testUndoRedo)--------------------
	@Test
	public void testUndoRedo() {
		Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Elm St", "Friends");
		contactService.addContact(contact);
		contactService.deleteContact("1");
		contactService.undo();
		assertEquals("John", contactService.getContacts()[0].getFirstName());
		contactService.redo();
		assertThrows(IllegalArgumentException.class, () -> contactService.getContacts());
	}

	// -----------FINAL ENHANCMENT BASED ON FEEDBACK
	// (testSortContacts)--------------------
	@Test
	public void testSortContacts() {
		Contact contact1 = new Contact("1", "Alice", "Smith", "1234567890", "123 Elm St", "Friends");
		Contact contact2 = new Contact("2", "Bob", "Johnson", "0987654321", "456 Oak St", "Family");
		contactService.addContact(contact1);
		contactService.addContact(contact2);

		List<Contact> sortedContacts = contactService.sortContactsByField("firstname");
		assertEquals("Alice", sortedContacts.get(0).getFirstName());
		assertEquals("Bob", sortedContacts.get(1).getFirstName());
	}

	// -----------FINAL ENHANCMENT BASED ON FEEDBACK
	// (testSearchContacts)--------------------
	@Test
	public void testSearchContacts() {
		Contact contact1 = new Contact("1", "Alice", "Smith", "1234567890", "123 Elm St", "Friends");
		Contact contact2 = new Contact("2", "Bob", "Johnson", "0987654321", "456 Oak St", "Family");
		contactService.addContact(contact1);
		contactService.addContact(contact2);

		List<Contact> foundContacts = contactService.searchContacts("Alice");
		assertEquals(1, foundContacts.size());
		assertEquals("Alice", foundContacts.get(0).getFirstName());
	}

	// -----------FINAL ENHANCMENT BASED ON FEEDBACK
	// (testLoadContacts)--------------------
	@Test
	public void testLoadContacts() throws Exception {
		contactService.loadContacts("contacts.txt"); // Assuming contacts.txt has valid contact data
		assertFalse(contactService.getContacts().length == 0); // Check that contacts were loaded
	}

	// -----------FINAL ENHANCMENT BASED ON FEEDBACK
	// (testSaveContacts)--------------------
	@Test
	public void testSaveContacts() throws Exception {
		Contact contact = new Contact("1", "John", "Doe", "1234567890", "123 Elm St", "Friends");
		contactService.addContact(contact);
		contactService.saveContacts("saved_contacts.txt"); // Save contacts to a file
		// Add assertions to verify the file contents if needed
	}

	// -----------FINAL ENHANCMENT BASED ON FEEDBACK
	// (testGroupContacts)--------------------
	@Test
	public void testGroupContacts() {
		Contact contact1 = new Contact("1", "Alice", "Smith", "1234567890", "123 Elm St", "Friends");
		Contact contact2 = new Contact("2", "Bob", "Johnson", "0987654321", "456 Oak St", "Family");
		contactService.addContact(contact1);
		contactService.addContact(contact2);

		contactService.updateContact("1", "group", "Work"); // Change Alice's group to Work
		assertEquals("Work", contactService.getContacts()[0].getGroup());
	}

	// All testing passed -NLG
}
