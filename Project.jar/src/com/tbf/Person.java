package com.tbf;

import java.util.ArrayList;

public class Person {
	
	private String code;
	private String firstName;
	private String lastName;
	private Address address;
	private ArrayList<String> email = new ArrayList<String>();
	
	
	
	public Person(String code, String firstName, String lastName, Address address) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	public Person(String code, String firstName, String lastName, Address address,
			ArrayList<String> email) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		for(String s:email) {
			this.email.add(s);
		}
		
	}
	public String getCode() {
		return this.code;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public Address getAddress() {
		return this.address;
	}
	public ArrayList<String> getEmail() {
		return this.email;
	}
	
	public double getfee() {
		return 0.0;
	}
	public double getCommision() {
		return 0.0;
	}
	public String getName() {
		return this.firstName + ", " + this.lastName;
	}
	
	

}
