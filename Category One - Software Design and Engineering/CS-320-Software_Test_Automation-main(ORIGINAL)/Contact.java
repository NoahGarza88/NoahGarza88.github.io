import junit.framework.TestCase;

package Contact;

public class Contact{
	
	//Initializing variables
	private String contactId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;

	



	
//Setter and Getter for contactId
public Contact(String contactId, String firstName,String lastName, String phone,String address) {
	if(contactId == null || contactId.length() > 10) {
		throw new IllegalArgumentException("Invalid contact Id.");
	}
	else {
		this.contactId = contactId;
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setAddress(address);
	}
	
}
public String getContactId() {	
	return contactId;
}




//Setter and Getter for firstName
public void setFirstName(String firstName) {
	if(firstName == null || firstName.length() > 10) {
		throw new IllegalArgumentException("Invalid entry for first name.");
	}
else {
	this.firstName = firstName;
}
}

public String getFirstName() {	
	return firstName;
}




//Setter and Getter for lastName
public void setLastName(String lastName) {	
	if(lastName == null || lastName.length() > 10) {
		throw new IllegalArgumentException("Invalid entry for last name.");
	}
	else {
	this.lastName = lastName;
	}
}

public String getLastName() {	
	return lastName;
}




//Setter and Getter for phone
public void setPhone(String phone) {	
	if(phone == null || phone.length() != 10) {		//UPDATED : Changed it to be exactly 10 digits as requested
		throw new IllegalArgumentException("Invalid entry for phone number");
	}
	else {
	this.phone= phone;
	}
}

public String getPhone() {	
	return phone;
}


//Setter and Getter for address
public void setAddress(String address) {
	if(address == null || address.length() > 30) {
		throw new IllegalArgumentException("Invalid entry for address.");
	}
	else {
	this.address = address;
	}
}

public String getAddress() {
	return address;
}
}