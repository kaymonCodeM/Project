/**
 * 
 * This file will access the database and convert the person
 * table into a java map with the key as the persons ID number.
 */


package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreatePersonSQL {


	
	/**
	 * This is a private method that is only used by the databaseToPerson() method.
	 * This method will create a person based on the persons type without having an email.
	 * 
	 * @param type - Three types: non broker, junior broker, expert broker
	 * @param sec - Broker Id
	 * @param code - Unique code to identify the person
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @return
	 */
	private static Person personWithoutEmail(String type, String sec, String code, String firstName, String lastName,Address address){
		if (sec!= null) {
			if (type.compareTo("Junior") == 0) {
				JuniorBroker juniorBroker = new JuniorBroker(sec, code, firstName, lastName, address);
				return juniorBroker;
			} else {
				ExpertBroker expertBroker = new ExpertBroker(sec, code, firstName, lastName, address);
				return expertBroker;
			}
		}
		Person person = new Person(code,firstName, lastName, address);
		return person;
	}
	/**
	 * This is a private method that is only used by the databaseToPerson() method.
	 * This method will create a person based on the persons type including email.
	 * 
	 * @param type - Three types: non broker, junior broker, expert broker
	 * @param sec - Broker Id
	 * @param code - Unique code to identify the person
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @return
	 */
	
	
	private static Person personWithEmail(String type, String sec, String code, String firstName,String lastName, Address address, ArrayList<String> email){
		if (sec !=null) {
			if (type.compareTo("Junior") == 0) {
				JuniorBroker juniorBroker = new JuniorBroker(sec, code, firstName, lastName, address,email);
				return juniorBroker;
			} else {
				ExpertBroker expertBroker = new ExpertBroker(sec, code, firstName, lastName, address, email);
				return expertBroker;
			}
		}
		Person person = new Person(code,firstName, lastName, address, email);
		return person;
	}
	/**
	 * This method will access the database and convert the Person table into a java map followed by the personId as the key.
	 * The query that is used to assess the Person class will be a left join to Email, Broker, Address, State, and Country tables.
	 * The information provided will allow the java Person class to create a person.
	 * @return
	 */
	
	public static Map<Integer,Person> databaseToPerson(){
		
		String query = "SELECT p.code AS code," + 
				"	    p.firstName AS firstName," + 
				"       p.lastName AS lastName," + 
				"       e.email AS email," + 
				"       b.codeSEC AS sec," + 
				"       b.type AS type," + 
				"       a.street AS street," + 
				"       a.city AS city," + 
				"       a.zipCode AS zip," + 
				"       s.state AS state," + 
				"       c.country AS country FROM Person p LEFT JOIN Email e ON p.personId = e.personId" + 
				"										   LEFT JOIN Broker b ON p.brokerId = b.brokerId" + 
				"										   LEFT JOIN Address a ON p.personId = a.personId" + 
				"                                          LEFT JOIN State s ON s.stateId = a.stateId" + 
				"                                          LEFT JOIN Country c ON c.countryId = s.countryId WHERE p.personId = ?;";
		
		Map<Integer,Person> result = new HashMap<Integer,Person>();
		Connection conn = mySQL.connectToSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = MaxSQL.numberOfPeople(conn);
		
		try {
			ps = conn.prepareStatement(query);
			for(int personId =1;personId<size+1;personId++) {
			ps.setInt(1,  personId);
			rs = ps.executeQuery();
			if (rs.next()) {
				
				//Person
				String code = rs.getString("code");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String sec = rs.getString("sec");
				String type = rs.getString("type");
				//Address
				String street = rs.getString("street");
				String city = rs.getString("city");
				String zip = rs.getString("zip");
				String state = rs.getString("state");
				String country = rs.getString("country");
				Address address = new Address(street, city, state, zip, country);
				
				//Create Person
				Person person =null;
				if(rs.getString("email")!=null) {
				//email
				ArrayList<String> email = new ArrayList<String>();
				email.add(rs.getString("email"));
				while(rs.next()) {
					email.add(rs.getString("email"));
				}
				person = personWithEmail(type, sec, code, firstName, lastName, address, email);
				result.put(personId,person);
				
				}else {
					person = personWithoutEmail(type, sec, code, firstName, lastName, address);
					result.put(personId,person);
				}

			}
			rs.close();
		}
			
			
			
		} catch (SQLException e) {

			throw new RuntimeException(e);
		}
		
		mySQL.clean(conn, ps, rs);
		return result;
	}
}
