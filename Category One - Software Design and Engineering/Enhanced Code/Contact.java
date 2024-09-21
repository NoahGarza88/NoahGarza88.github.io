
package Contact;

public class Contact{ //Start of the class Contact
	
	//Initializing variables
	private String contactId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;

	
	//ENHANCEMENT - Final Integers
	private static final int MaxContactIDLength = 10;
	private static final int MaxFirstNameLength = 10;
	private static final int MaxLastNameLength = 10;
	private static final int MaxPhoneNumberLength = 10;
	private static final int MaxAddressLength = 30;
			
			
	//ENHANCEMENT - FINAL STRINGS
	private static final String ConstructorExceptionMessage = "Invalid contact Id.";
	private static final String SetFirstNameExceptionMessage = "Invalid entry for first name.";
	private static final String LastNameExceptionMessage = "Invalid entry for last name.";
	private static final String PhoneNumberExceptionMessage = "Invalid entry for phone number";
	private static final String AddressExceptionMessage = "Invalid entry for address.";
	



	
//Setter and Getter for contactId
public Contact(String contactId, String firstName,String lastName, String phone,String address) {
	
		//ENHANCEMENT - IF statment to check if contactId is null or the length is greater than its limit, if either an exception is thrown
	if(contactId == null || contactId.length() > MaxContactIDLength) { 
		throw new IllegalArgumentException(ConstructorExceptionMessage);
	}
	else { //ELSE, set the variables to appropirate setters.
		this.contactId = contactId;
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setAddress(address);
	}	
}

//ENHANCEMENT- Getter for the contactID
public String getContactId() {	
	return contactId;
}




//Setter and Getter for firstName
public void setFirstName(String firstName) {
	if(firstName == null || firstName.length() > MaxFirstNameLength) { //ENHANCED
		throw new IllegalArgumentException(SetFirstNameExceptionMessage);
	}
else {
	this.firstName = firstName;
}
}


//ENHANCEMENT - Getter for the first name
public String getFirstName() {	
	return firstName;
}




//Setter and Getter for lastName
public void setLastName(String lastName) {	
	if(lastName == null || lastName.length() > MaxLastNameLength) {
		throw new IllegalArgumentException(LastNameExceptionMessage);
	}
	else {
	this.lastName = lastName;
	}
}

//ENHANCEMENT - Getter for the last name.
public String getLastName() {	
	return lastName;
}




//Setter and Getter for phone
public void setPhone(String phone) {	
	if(phone == null || phone.length() != MaxPhoneNumberLength) {		//UPDATED : Changed it to be exactly 10 digits as requested
		throw new IllegalArgumentException(PhoneNumberExceptionMessage);
	}
	else {
	this.phone= phone;
	}
}

//ENHANCEMENT - Getter for returning the phone
public String getPhone() {	
	return phone;
}


//Setter and Getter for address
public void setAddress(String address) {
	if(address == null || address.length() > MaxAddressLength) {
		throw new IllegalArgumentException(AddressExceptionMessage);
	}
	else {
	this.address = address;
	}
}


//ENHANCEMENT - Getter for the address
public String getAddress() {
	return address;
}
}