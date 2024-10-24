package Contact;

import java.util.*;
import java.io.*;

public class ContactService {
    private Map<String, Contact> contacts;
    private Stack<Contact> history; // For undo functionality
    private Stack<Contact> redoStack; // For redo functionality

    // ENHANCEMENTS - Error messages as constants
    private static final String CONTACT_NULL_ERROR = "Error: Contact must not be null.";
    private static final String CONTACT_EXISTS_ERROR = "Error: Contact already exists.";
    private static final String CONTACT_NOT_FOUND_ERROR = "Error: Contact ID does not exist.";
    private static final String INVALID_FIELD_ERROR = "Error: Invalid field name.";
    private static final String UPDATE_CONTACT_ERROR = "Error: Contact ID, field, and value must not be null.";

    public ContactService() {
        contacts = new HashMap<>();
        history = new Stack<>();
        redoStack = new Stack<>();
    }

    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException(CONTACT_NULL_ERROR);
        }

        String id = contact.getContactId();
        if (contacts.containsKey(id)) {
            throw new IllegalArgumentException(CONTACT_EXISTS_ERROR);
        }

        contacts.put(id, contact);
        history.push(contact); // Push to history for undo
        redoStack.clear(); // Clear redo stack on new addition
    }

    public void deleteContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Error: Contact ID must not be null.");
        }

        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException(CONTACT_NOT_FOUND_ERROR);
        }

        Contact removedContact = contacts.remove(contactId);
        history.push(removedContact); // Push to history for undo
        redoStack.clear(); // Clear redo stack on deletion
    }

    public Contact updateContact(String contactId, String field, String value) {
        if (contactId == null || field == null || value == null) {
            throw new IllegalArgumentException(UPDATE_CONTACT_ERROR);
        }

        Contact contactToUpdate = contacts.get(contactId);
        if (contactToUpdate == null) {
            throw new IllegalArgumentException(CONTACT_NOT_FOUND_ERROR);
        }

        Contact backupContact = new Contact(contactToUpdate.getContactId(), contactToUpdate.getFirstName(),
                contactToUpdate.getLastName(), contactToUpdate.getPhone(), contactToUpdate.getAddress(),
                contactToUpdate.getGroup());
        switch (field) {
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
            case "group":
                contactToUpdate.setGroup(value);
                break;
            default:
                throw new IllegalArgumentException(INVALID_FIELD_ERROR);
        }

        history.push(backupContact); // Push the backup for undo
        redoStack.clear(); // Clear redo stack on update
        return contactToUpdate; // Optionally return the updated contact
    }

    // -----------FINAL ENHANCMENT BASED ON FEEDBACK (undo)--------------------
    public void undo() {
        if (!history.isEmpty()) {
            Contact contact = history.pop();
            contacts.remove(contact.getContactId());
            redoStack.push(contact); // Push to redo stack
        }
    }

    // -----------FINAL ENHANCMENT BASED ON FEEDBACK (redo)--------------------
    public void redo() {
        if (!redoStack.isEmpty()) {
            Contact contact = redoStack.pop();
            contacts.put(contact.getContactId(), contact);
            history.push(contact); // Push back to history
        }
    }

    public Contact[] getContacts() {
        return contacts.values().toArray(new Contact[0]); // Return an array of contacts
    }

    // -----------FINAL ENHANCMENT BASED ON FEEDBACK
    // (sortContactsByField)--------------------
    public List<Contact> sortContactsByField(String field) {
        List<Contact> contactList = new ArrayList<>(contacts.values());
        contactList.sort((c1, c2) -> {
            switch (field.toLowerCase()) {
                case "firstname":
                    return c1.getFirstName().compareTo(c2.getFirstName());
                case "lastname":
                    return c1.getLastName().compareTo(c2.getLastName());
                case "phone":
                    return c1.getPhone().compareTo(c2.getPhone());
                case "address":
                    return c1.getAddress().compareTo(c2.getAddress());
                case "group":
                    return c1.getGroup().compareTo(c2.getGroup());
                default:
                    throw new IllegalArgumentException("Invalid field for sorting.");
            }
        });
        return contactList; // Return sorted list of contacts
    }

    // -----------FINAL ENHANCMENT BASED ON FEEDBACK
    // (searchContacts)--------------------
    public List<Contact> searchContacts(String regex) {
        List<Contact> matchedContacts = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);

        for (Contact contact : contacts.values()) {
            if (pattern.matcher(contact.getFirstName()).find() ||
                    pattern.matcher(contact.getLastName()).find() ||
                    pattern.matcher(contact.getPhone()).find() ||
                    pattern.matcher(contact.getAddress()).find() ||
                    pattern.matcher(contact.getGroup()).find()) {
                matchedContacts.add(contact);
            }
        }
        return matchedContacts; // Return matching contacts
    }

    // -----------FINAL ENHANCMENT BASED ON FEEDBACK
    // (loadContacts)--------------------
    public void loadContacts(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Contact contact = new Contact(values[0], values[1], values[2], values[3], values[4], values[5]);
                addContact(contact);
            }
        }
    }

    // -----------FINAL ENHANCMENT BASED ON FEEDBACK
    // (saveContacts)--------------------
    public void saveContacts(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts.values()) {
                bw.write(String.join(",", contact.getContactId(), contact.getFirstName(), contact.getLastName(),
                        contact.getPhone(), contact.getAddress(), contact.getGroup()));
                bw.newLine(); // New line for each contact
            }
        }
    }
}
