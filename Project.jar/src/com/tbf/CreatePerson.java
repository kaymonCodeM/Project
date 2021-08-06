package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreatePerson {

	/**
	 * This method checks if a person is a Expert or junior broker and returns a person without a email address.
	 * @param sec
	 * @param code
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @return
	 */
	
	public static Person personWithoutEmail(String sec, String code, String firstName,String lastName,Address address){
		if (sec.compareTo("") != 0) {
			String brokerTokens[] = sec.split(",");
			if (brokerTokens[0].compareTo("J") == 0) {
				JuniorBroker juniorBroker = new JuniorBroker(brokerTokens[1], code, firstName, lastName, address);
				return juniorBroker;
			} else {
				ExpertBroker expertBroker = new ExpertBroker(brokerTokens[1], code, firstName, lastName, address);
				return expertBroker;
			}
		}
		Person person = new Person(code,firstName, lastName, address);
		return person;
	}
	
	
	
	/**
	 *  This method creates a person that has one or more email(s) and checks if the person is a JuniorBroker or a ExpertBroker
	 *  If not create a normal person.
	 * @param sec
	 * @param code
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param emails
	 * @return
	 */
	public static Person personWithEmail(String sec, String code, String firstName,String lastName,Address address,String emails){
		ArrayList<String> email = new ArrayList<String>();
		String emailTokens[] = emails.split(",");
		for (String e : emailTokens) {
			email.add(e);
		}
		
		if (sec.compareTo("") != 0) {
			String brokerTokens[] = sec.split(",");
			if (brokerTokens[0].compareTo("J") == 0) {
				JuniorBroker juniorBroker = new JuniorBroker(brokerTokens[1], code, firstName, lastName, address,email);
				return juniorBroker;
			} else {
				ExpertBroker expertBroker = new ExpertBroker(brokerTokens[1], code, firstName, lastName, address, email);
				return expertBroker;
			}
		}
		
		Person person = new Person(code,firstName, lastName, address, email);
		
		return person;
		
	}


	/**
	 * This method will take in a file and convert the file into a class of a
	 * Person. A person will have a Person Code, Broker data, Name, Address, and a
	 * optional Email address(es).
	 */

	public static Map<String,Person> fileToPerson() {
		Map<String,Person> result = new HashMap<String,Person>();
		File f = new File("data/Persons.dat");
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		String line = s.nextLine();
		int numberOfLines = Integer.parseInt(line);

		for (int i = 0; i < numberOfLines; i++) {
			line = s.nextLine();
			String personTokens[] = line.split(";");
			String code = personTokens[0];
			String nameTokens[] = personTokens[2].split(",");
			String firstName = nameTokens[0];
			String lastName = nameTokens[1];
			String addressTokens[] = personTokens[3].split(",");
			String street = addressTokens[0];
			String city = addressTokens[1];
			String state = addressTokens[2];
			String zip = addressTokens[3];
			String country = addressTokens[4];
			Address address = new Address(street, city, state, zip, country);
			String sec = personTokens[1];
			if (personTokens.length == 5) {
				Person person = personWithEmail(sec, code, firstName, lastName, address, personTokens[4]);
				result.put(person.getCode(),person);
			}else {
				Person person = personWithoutEmail(sec, code, firstName, lastName, address);
				result.put(person.getCode(),person);
			}

			
			
		}
		s.close();
		return result;
	}
}
