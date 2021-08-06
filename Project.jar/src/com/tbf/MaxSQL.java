package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This class is meant to return a max amount of primary keys within a specific table within the database.
 * Every method within this class DOES NOT close the connection!
 * @author Kaymon McCain
 *
 */

public class MaxSQL {
	/**
	 * This method will used the connection to the database and return the highest asset id
	 * within the database so every asset will be utilized in a loop
	 * @return
	 */
	protected static int numberOfAsset(Connection conn) {
	String query = "SELECT max(assetId) AS numberOfAsset FROM Asset;";
	PreparedStatement ps = null;
	ResultSet rs = null;
	int count = 0;

	try {
		ps = conn.prepareStatement(query);
		rs = ps.executeQuery();
		
		if(rs.next()) {
		count = rs.getInt("numberOfAsset");
		}
	} catch (SQLException e) {

		throw new RuntimeException(e);
	}
	
	mySQL.clean(null, ps, rs);
	return count;
	}
	/**
	 * This method is a private method that is only used by the databaseToPortfolio() method.
	 * The query used to access the database will get the highest portfolioId for a loop to 
	 * access every portfolio in the database.
	 * @param conn
	 * @return
	 */

	public static int numberOfPortfolio(Connection conn) {
	String query = "SELECT max(portfolioId) AS numberOfPortfolio FROM Portfolio;";
	PreparedStatement ps = null;
	ResultSet rs = null;
	int count = 0;

	try {
		ps = conn.prepareStatement(query);
		rs = ps.executeQuery();
		
		if(rs.next()) {
		count = rs.getInt("numberOfPortfolio");
		}
	} catch (SQLException e) {

		throw new RuntimeException(e);
	}
	
	mySQL.clean(null, ps, rs);
	return count;
	}
	/**
	 * This is a private method that will only be used by the databaseToPerson() method.
	 * This method will use the input connection to access the database and return the highest personId that will give a
	 * good value for a loop to access every person inside of the database.
	 * @param conn
	 * @return
	 */
	public static int numberOfPeople(Connection conn) {
	
	String query = "SELECT max(personId) AS numberOfPeople FROM Person;";
	PreparedStatement ps = null;
	ResultSet rs = null;
	int count = 0;

	try {
		ps = conn.prepareStatement(query);
		rs = ps.executeQuery();
		
		if(rs.next()) {
		count = rs.getInt("numberOfPeople");
		}
	} catch (SQLException e) {

		throw new RuntimeException(e);
	}
	
	mySQL.clean(null, ps, rs);
	return count;
	}
}
