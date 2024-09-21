package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Contact.Contact;

class ContactTest {
	private Contact contact;

	//Creating contact objects before each test is run.
	@BeforeEach
	public void createContact() {
		contact = new Contact("1", "Noah", "Garza", "8888888888", "8 Penguin Dr, Mission TX, 7887");
	}

	//Verifying object creation
	@Test
	void testContactClass() {
		assertTrue(contact.getContactId().equals("1"));
		assertTrue(contact.getFirstName().equals("Noah"));
		assertTrue(contact.getLastName().equals("Garza"));
		assertTrue(contact.getPhone().equals("8888888888"));
		assertTrue(contact.getAddress().equals("8 Penguin Dr, Mission TX, 7887"));
	}
	
	//Verifying if exception is caught if the name is too long.
	@Test
	void testContactFirstNameTooLong() {
		assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("Iamenjoyingthisclassasitprovidesagoodamountofchallenge"));
	}
	
	
	
	//UPDATED: TEST TO VERIFY THAT THE LAST NAME IS LESS THAN 10 CHARACTERS
		@Test
		void testContactLastNameTooLong() {
			assertThrows(IllegalArgumentException.class, () -> contact.setLastName("I updated the last name to be longer than 10 characters"));
		}
	
	
		
		
	//Verifying null check for firstName.
	@Test
	void testContactWithNoFirstName() {
		assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
	}


	//Verifying null check for lastName.
	@Test
        void testContactWithNoLastName() {
		assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
	}
	
	//Verifying null check for phone number.
	@Test
        void testContactWithNoPhone() {
		assertThrows(IllegalArgumentException.class, () -> contact.setPhone(null));
	}
	
		
	
	
	///UPDATED: TEST TO CHECK THAT PHONE MUST BE 10 CHARACTERS
		@Test
	        void testPhoneLengthNotTen() {
			assertThrows(IllegalArgumentException.class, () -> contact.setPhone("99999"));	
			assertThrows(IllegalArgumentException.class, () -> contact.setPhone("99999999999"));
		}

		
		
		
		
	//Verifying null check for address.
	@Test
        void testContactWithNoAddress() {
		assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null));
	}
	
	
	//UPDATED: TEST TO CHECK IF ADDRESS IS LESS THAN 30 CHARACTERS
	
	@Test
		void testAddressLength() {
		assertThrows(IllegalArgumentException.class, ()-> contact.setAddress("123456789012345678901234567890 30"));
	}

}

