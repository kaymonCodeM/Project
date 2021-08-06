package com.tbf;

import java.util.ArrayList;

public class JuniorBroker extends Person {

	private String sec;



	public JuniorBroker(String sec,String code, String firstName, String lastName, Address address) {
		super(code, firstName, lastName, address);
		this.sec = sec;
	}
	

	public JuniorBroker(String sec,String code, String firstName, String lastName, Address address, ArrayList<String>email) {
		super(code, firstName, lastName, address,email);
		this.sec = sec;
	}


	public String getSec() {
		return sec;
	}
	
	public double getfee() {
		return 75.0;
	}
	
	public double getCommision() {
		return 0.0125;
	}

}
