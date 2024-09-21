package Contact;

//ENHANCED - USED HASHMAP
import java.util.Map;
import java.util.HashMap;

public class ContactService {
    private Map<String, Contact> contacts; // Creating A hashmap of contacts.

    // ENHANCEMENTS - Error messages as constants
    private static final String CONTACT_NULL_ERROR = "Error: Contact must not be null.";
    private static final String CONTACT_EXISTS_ERROR = "Error: Contact already exists.";
    private static final String CONTACT_NOT_FOUND_ERROR = "Error: Contact ID does not exist.";
    private static final String INVALID_FIELD_ERROR = "Error: Invalid field name.";
    private static final String UPDATE_CONTACT_ERROR = "Error: Contact ID, field, and value must not be null.";

    public ContactService() { // ENHANCEMENT - WHEN CREATED A ARRAYLIST CALLED CONTACTS IS CREATED
        contacts = new HashMap<>();
    }

    // ENHANCMENT- Function for adding a contact
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException(CONTACT_NULL_ERROR);
        }

        String id = contact.getContactId();
        if (contacts.containsKey(id)) {
            throw new IllegalArgumentException(CONTACT_EXISTS_ERROR);
        }

        contacts.put(id, contact);
    }

    // ENHANCEMENT - Method for deleting contacts
    public void deleteContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Error: Contact ID must not be null.");
        }

        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException(CONTACT_NOT_FOUND_ERROR);
        }

        contacts.remove(contactId);
    }

    // ENHANCEMENT - Method for updating contacts
    public Contact updateContact(String contactId, String field, String value) {
        if (contactId == null || field == null || value == null) {
            throw new IllegalArgumentException(NULL_FIELD_VALUE_ERROR);
        }

        Contact contactToUpdate = contacts.get(contactId);
        if (contactToUpdate == null) {
            throw new IllegalArgumentException(CONTACT_NOT_FOUND_ERROR);
        }

        switch (field) { // ENHANCEMENT - USED A SWITCH STATEMENT
            case "firstName":
                contactToUpdate.setFirstName(value);
                break;
            case "lastName":
                contactToUpdate.setLastName(value);
                break;
            case "phone":
                contactToUpdate.setPhone(value);
                break;
            case "address":
                contactToUpdate.setAddress(value);
                break;
            default:
                throw new IllegalArgumentException(INVALID_FIELD_ERROR);
        }

        return contactToUpdate; // Optionally return the updated contact
    }

    // Method to return the contact list as an array
    public Contact[] getContacts() {
        return contacts.values().toArray(new Contact[0]); // Return an array of contacts
    }

}// End
