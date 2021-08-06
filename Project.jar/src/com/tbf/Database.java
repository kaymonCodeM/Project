package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *  This class supports other classes within the project having access to primary keys within the database.
 *  Every method within this class DOES NOT close the connection!
 * @author Kaymon McCain
 *
 */
public class Database {

	
	/**
	 * This function will return the last inserted Id.
	 * @param conn
	 * @return
	 */
	public static int getLastInsertId(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs =null;
		int result = 0;
		try {
		ps = conn.prepareStatement("select last_insert_id() as newId;");
		rs = ps.executeQuery();
		if(rs.next()) {
			result = rs.getInt("newId");
		}
		}catch (SQLException e) {

			throw new RuntimeException(e);
		}
		mySQL.clean(null,ps,rs);
		return result;
	}
	
	/**
	 * This method will return a personId on the given personCode.
	 * @param personCode
	 * @return
	 */
	public static int getPersonId(String personCode,Connection conn) {
		int personId = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		ps = conn.prepareStatement("select if(exists(select personId from Person where code = ?),"
								+ "(select personId from Person where code = ?),0) as personId;");
		ps.setString(1, personCode);
		ps.setString(2, personCode);
		rs = ps.executeQuery();
		
		if(rs.next()) {
			personId = rs.getInt("personId");
		}
		}catch (SQLException e) {

			throw new RuntimeException(e);
		}
		
		mySQL.clean(null, ps, rs);
		return personId;
		
	}
	/**
	 * This function will return a stateId.
	 * The function will first search for a stateId based on the given state:
	 * if no state Id -> search for country Id.
	 *     if no country Id -> insert country, and state.
	 * 			if country Id do exist but no state Id -> insert state to the country.
	 * if state Id exists -> return stateId.
	 * 
	 */
	public static int getStateId(String state, String country,Connection conn) {
		int stateId =0;
		int countryId =0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select if(exists(select stateId from State where state = ?),"
					               + "(select stateId from State where state = ?),0) as stateId;");
			ps.setString(1, state);
			ps.setString(2, state);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				stateId = rs.getInt("stateId");
			}
			mySQL.clean(null, ps, rs);
			if(stateId==0) {
				ps = conn.prepareStatement("select if(exists(select countryId from Country where country = ?),"
						              + "(select countryId from Country where country = ?),0) as countryId;");
				ps.setString(1, country);
				ps.setString(2, country);
				rs = ps.executeQuery();
				if(rs.next()) {
					countryId = rs.getInt("countryId");
				}
				mySQL.clean(null, ps, rs);
				if(countryId==0) {
					ps = conn.prepareStatement("insert into Country(country) value (?);");
					ps.setString(1, country);
					ps.executeUpdate();
					mySQL.clean(null, ps, rs);
					countryId = getLastInsertId(conn);
				}	
				ps = conn.prepareStatement("insert into State(state,countryId) values (?,?);");
				ps.setString(1,state);
				ps.setInt(2, countryId);
				ps.executeUpdate();
				mySQL.clean(null, ps, rs);
				stateId = getLastInsertId(conn);
			
			}
			
		}catch (SQLException e) {

			throw new RuntimeException(e);
		}
		mySQL.clean(null, ps, rs);
		return stateId;
	}
	/**
	 * This method will capture the assetId to the database based on the assetCode;
	 * If there is no asset return zero;
	 */
	public static int getAssetId(String assetCode,Connection conn) {
		int assetId = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select if(exists(select assetId from Asset where code = ?),"
					                + "(select assetId from Asset where code = ?),0) as assetId;");
			ps.setString(1, assetCode);
			ps.setString(2,assetCode);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				assetId = rs.getInt("assetId");
			}
		}catch (SQLException e) {

			throw new RuntimeException(e);
		}
		mySQL.clean(null, ps, rs);
		return assetId;
	}
	/**
	 * This method will capture the portfolioId from the database
	 * @param portfolioCode
	 * @param conn
	 * @return
	 */
	public static int getPortfolioId(String portfolioCode,Connection conn) {
		int portfolioId = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		ps = conn.prepareStatement("select portfolioId from Portfolio where code = ?;");
		ps.setString(1, portfolioCode);
		rs = ps.executeQuery();
		
		if(rs.next()) {
			portfolioId = rs.getInt("portfolioId");
		}
		}catch (SQLException e) {

			throw new RuntimeException(e);
		}
		
		mySQL.clean(null, ps, rs);
		return portfolioId;
	}
	/**
	 * this method will return the brokerId on the given personId
	 * This function will return zero if broker does not exist
	 */
	public static Integer getBrokerId(int personId, Connection conn) {
		Integer brokerId =0;
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select brokerId from Person where personId =?;");
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			if(rs.next()) {
				brokerId = rs.getInt("brokerId");
			}
			
		}catch (SQLException e) {

			throw new RuntimeException(e);
		}
		
		mySQL.clean(null, ps, rs);
		return brokerId;
	}
}

