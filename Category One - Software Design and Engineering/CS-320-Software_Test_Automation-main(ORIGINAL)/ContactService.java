package Contact;

import java.util.ArrayList;

public class ContactService {
	private  ArrayList<Contact> contacts;	//Creating A array list of contacts.
	
	
	public ContactService() {
		contacts = new ArrayList<>();
	}

	//Function for adding a contact
	public void addContacts(Contact contact) {
		if (contact == null) {
			throw new IllegalArgumentException("Error, contact must not be null");
		}
		else{
			String id = contact.getContactId();
			boolean exist = false;
			for(Contact people:contacts) {
				if(people.getContactId().equals(id)) {
					exist = true;
				}	
			}
			if(exist != true) {
				contacts.add(contact);
			}
			else { throw new IllegalArgumentException("Contact already exists");}
		}
	}
	
	
	//Method for deleting contacts
	public void deleteContacts(String contactId) {
		boolean removed = false;
		if(contactId != null) {
		for(Contact person: contacts) {
			if(person.getContactId().equals(contactId)){
				contacts.remove(person);
				removed = true;
				break;
			}
		}
		if (removed == false) {
			throw new IllegalArgumentException("Contact id does not exist!");}
	}
}
	
	//Method for updating contacts
	public void updateContact(String contactId, String field, String value) {
		if(contactId != null && field != null && value != null) {
		for(Contact people: contacts) {
			if(people.getContactId().equals(contactId)){
				if(field.equals("firstName")) {people.setFirstName(value);}
				else if(field.equals("lastName")){ people.setLastName(value);}
				else if(field.equals("phone")) {people.setPhone(value);}
				else if(field.equals("address")) {people.setAddress(value);}
				
				}
			}
		}
		else {throw new IllegalArgumentException("Contact ID and field must not be null");}
	}
	
	
	//Method to return the contact lists
	public ArrayList<Contact> getContacts(){
		return contacts;
	}
	
}//End
	

