
	package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

	/**
	 * This is a collection of utility methods that define a general API for
	 * interacting with the database supporting this application.
	 * The first five methods are private methods only used within class to support the overall collection of methods.
	 * The methods within the class pertains to inserter and deleting within the database.
	 *
	 */
	public class PortfolioData {
		
		private static Connection conn = null;
		
		/**
		 * This method takes in secBrokerId and broker Type and inserts a new broker into the database.
		 * The output will return the brokerId
		 */
		private static int insertBroker(String secBrokerId,String brokerType) {
			int brokerId =0;
			PreparedStatement ps = null;

			try {
				ps = conn.prepareStatement("insert into Broker(codeSEC,type,commissionRate,fee) values (?,?,?,?);");
				ps.setString(1, secBrokerId);
				if(brokerType.charAt(0)== 'E') {
					ps.setString(2, "Expert");
					ps.setDouble(3, 0.0375);
					ps.setDouble(4, 0.0);
				}else {
					ps.setString(2, "Junior");
					ps.setDouble(3, 0.0125);
					ps.setDouble(4, 75.0);
				}
				ps.executeUpdate();
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
		
			
			mySQL.clean(null, ps, null);
			brokerId = Database.getLastInsertId(conn);
			return brokerId;
			
		}
		/**
		 * This method deletes a broker from the database
		 * 
		 */
		private static void removeBroker(int brokerId) {
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement("update Portfolio set managerId = null where managerId = ?;");
				ps.setInt(1, brokerId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Broker where brokerId =?;");
				ps.setInt(1, brokerId);
				ps.executeUpdate();
				ps.close();
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(null, ps, null);
			return;
		}
		/**
		 * This method inserts a person inside the database based on the inputs. If the person is a broker then the
		 * brokerId must be greater than zero.
		 * @param personCode
		 * @param firstName
		 * @param lastName
		 * @param brokerId
		 * @return
		 */
		
		private static int insertPerson(String personCode,String firstName,String lastName,int brokerId) {
			int personId =0;
			String query ="";
			PreparedStatement ps = null;
			if(brokerId==0) {
				query += "insert into Person(code,firstName,lastName) values ( ?,?,?);";
			}else {
				query += "insert into Person(code,firstName,lastName,brokerId) values ( ?,?,?,?);";
			}
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, personCode);
				ps.setString(2, firstName);
				ps.setString(3, lastName);
				if(brokerId>0) {
					ps.setInt(4, brokerId);
				}
				ps.executeUpdate();
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			
			mySQL.clean(null, ps, null);
			personId = Database.getLastInsertId(conn);
			return personId;
		}
		/**
		 * This method will insert a new address into the database based on the given inputs.
		 * @param street
		 * @param city
		 * @param zip
		 * @param stateId
		 * @param personId
		 */
		private static void insertAddress(String street, String city, String zip, int stateId, int personId) {
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement("insert into Address(street,city,stateId,zipCode,personId) values (?,?,?,?,?);");
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setInt(3, stateId);
				ps.setString(4, zip);
				ps.setInt(5, personId);
				ps.executeUpdate();
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(null, ps, null);
			return;
		}
		/**
		 * This method will insert a asset into the database and return the assetId.
		 */
		private static int insertAsset(String assetCode, String type, String label) {
			PreparedStatement ps =null;
			int assetId =0;
			try {
				ps = conn.prepareStatement("insert into Asset(code,type,label) values (?,?,?);");
				ps.setString(1, assetCode);
				ps.setString(2, type);
				ps.setString(3, label);
				ps.executeUpdate();
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(null, ps, null);
			assetId = Database.getLastInsertId(conn);
			return assetId;
		}

		/**
		 * Method that removes every person record from the database
		 */
		public static void removeAllPersons() {
			
			conn = mySQL.connectToSQL();
			int numberOfPeople = MaxSQL.numberOfPeople(conn);
			PreparedStatement ps = null;
			try {
				for(int personId =1; personId<numberOfPeople+1;personId++) {
				Integer brokerId = Database.getBrokerId(personId, conn);
				ps = conn.prepareStatement("delete from Account where portfolioId = "
						     + "(select portfolioId from Portfolio where ownerId=?);");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Portfolio  where ownerId = ?");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Address where personId =?;");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Email where personId =?;");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("update Portfolio set beneficiaryId = null where beneficiaryId =?;");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Person where personId =?;");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				if(brokerId!=null) {
					removeBroker(brokerId);
				}
				}
				
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}
		
		/**
		 * Removes the person record from the database corresponding to the
		 * provided <code>personCode</code>
		 * @param personCode
		 */
		public static void removePerson(String personCode) {
			conn = mySQL.connectToSQL();
			int personId = Database.getPersonId(personCode,conn);
			Integer brokerId = Database.getBrokerId(personId, conn);
			PreparedStatement ps = null;
			
			try {
				ps = conn.prepareStatement("delete from Account where portfolioId = "
						     + "(select portfolioId from Portfolio where ownerId=?);");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Portfolio  where ownerId = ?");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Address where personId =?");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Email where personId =?");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("update Portfolio set beneficiaryId = null where beneficiaryId =?;");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Person where personId =?");
				ps.setInt(1, personId);
				ps.executeUpdate();
				ps.close();
				if(brokerId!=null) {
					removeBroker(brokerId);
				}
				
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}

		
		/**
		 * Method to add a person record to the database with the provided data. The
		 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
		 * <code>null</code> if the person is not a broker.
		 * @param personCode
		 * @param firstName
		 * @param lastName
		 * @param street
		 * @param city
		 * @param state
		 * @param zip
		 * @param country
		 * @param brokerType
		 */
		public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, String brokerType, String secBrokerId) {
			conn = mySQL.connectToSQL();
			int personId = Database.getPersonId(personCode, conn);
			int brokerId = 0;
			if(personId>0) {
				mySQL.clean(conn, null, null);
				return;
			}
			if(secBrokerId!=null) {
				brokerId = insertBroker(secBrokerId,brokerType);			
			}
			personId = insertPerson(personCode,firstName,lastName,brokerId);
			int stateId = Database.getStateId(state,country,conn);
			insertAddress(street,city,zip,stateId,personId);
			mySQL.clean(conn, null, null);
			return;
		}
		
		/**
		 * Adds an email record corresponding person record corresponding to the
		 * provided <code>personCode</code>
		 * @param personCode
		 * @param email
		 */
		public static void addEmail(String personCode, String email) {
			conn = mySQL.connectToSQL();
			int personId = Database.getPersonId(personCode,conn);
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement("insert into Email(email,personId) values (?,?);");
				ps.setString(1, email);
				ps.setInt(2, personId);
				ps.executeUpdate();
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}



		/**
		 * Removes all asset records from the database
		 */
		public static void removeAllAssets() {
			conn = mySQL.connectToSQL();
			int numberOfAssets = MaxSQL.numberOfAsset(conn);
			PreparedStatement ps = null;
			try {
				for(int assetId =1; assetId<numberOfAssets+1;assetId++) {
				ps = conn.prepareStatement("delete from Account where assetId=?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Stock where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from PrivateInvestment where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from DepositAccount where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Asset where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();		
				}
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
			
		}

		/**
		 * Removes the asset record from the database corresponding to the
		 * provided <code>assetCode</code>
		 * @param assetCode
		 */
		public static void removeAsset(String assetCode) {
			conn = mySQL.connectToSQL();
			int assetId = Database.getAssetId(assetCode,conn);
			PreparedStatement ps =null;
			try {
				ps = conn.prepareStatement("delete from Account where assetId=?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Stock where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from PrivateInvestment where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from DepositAccount where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Asset where assetId =?;");
				ps.setInt(1, assetId);
				ps.executeUpdate();
				ps.close();	
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}

		/**
		 * Adds a deposit account asset record to the database with the
		 * provided data. 
		 * @param assetCode
		 * @param label
		 * @param apr
		 */
		public static void addDepositAccount(String assetCode, String label, double apr) {
			conn = mySQL.connectToSQL();
			int assetId = Database.getAssetId(assetCode, conn);
			if(assetId>0) {
				mySQL.clean(conn, null, null);
				return;
			}
			assetId = insertAsset(assetCode,"Deposit Account",label);
			PreparedStatement ps =null;
			try {
				ps = conn.prepareStatement("insert into DepositAccount(assetId,apr) values (?,?);");
				ps.setInt(1, assetId);
				ps.setDouble(2, apr*100);
				ps.executeUpdate();
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
			
		}
		
		/**
		 * Adds a private investment asset record to the database with the
		 * provided data. 
		 * @param assetCode
		 * @param label
		 * @param quarterlyDividend
		 * @param baseRateOfReturn
		 * @param baseOmega
		 * @param totalValue
		 */
		public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 
				Double baseRateOfReturn, Double baseOmega, Double totalValue) {
			conn = mySQL.connectToSQL();
			int assetId = Database.getAssetId(assetCode, conn);
			assetId = insertAsset(assetCode,"Private Investment",label);
			PreparedStatement ps =null;
			try {
				ps = conn.prepareStatement("insert into PrivateInvestment(assetId,quarterlyDividend,baseRateOfReturn,baseOmegaMeasure,totalValue) values (?,?,?,?,?);");
				ps.setInt(1, assetId);
				ps.setDouble(2, quarterlyDividend);
				ps.setDouble(3, baseRateOfReturn*100);
				ps.setDouble(4, baseOmega);
				ps.setDouble(5, totalValue);
				ps.executeUpdate();
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
			
		}
		
		/**
		 * Adds a stock asset record to the database with the
		 * provided data. 
		 * @param assetCode
		 * @param label
		 * @param quarterlyDividend
		 * @param baseRateOfReturn
		 * @param beta
		 * @param stockSymbol
		 * @param sharePrice
		 */
		public static void addStock(String assetCode, String label, Double quarterlyDividend, 
				Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {
				conn = mySQL.connectToSQL();
				int assetId = Database.getAssetId(assetCode, conn);
				assetId = insertAsset(assetCode,"Stock",label);
				PreparedStatement ps =null;
				try {
					ps = conn.prepareStatement("insert into Stock(assetId,quarterlyDividend,baseRateOfReturn,betaMeasure,stockSymbol,sharePrice) values (?,?,?,?,?,?);");
					ps.setInt(1, assetId);
					ps.setDouble(2, quarterlyDividend);
					ps.setDouble(3, baseRateOfReturn*100);
					ps.setDouble(4, beta);
					ps.setString(5, stockSymbol);
					ps.setDouble(6, sharePrice);
					ps.executeUpdate();
				}catch (SQLException e) {

					throw new RuntimeException(e);
				}
				mySQL.clean(conn, ps, null);
				return;
		}

		/**
		 * Removes all portfolio records from the database
		 */
		public static void removeAllPortfolios() {
			conn = mySQL.connectToSQL();
			int numberOfPortfolio = MaxSQL.numberOfPortfolio(conn);
			PreparedStatement ps =null;
			try {
				for(int portfolioId =1; portfolioId<numberOfPortfolio+1;portfolioId++) {
				ps = conn.prepareStatement("delete from Account where portfolioId =?;");
				ps.setInt(1, portfolioId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Portfolio where portfolioId =?;");
				ps.setInt(1, portfolioId);
				ps.executeUpdate();
				ps.close();
				}
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}
		
		/**
		 * Removes the portfolio record from the database corresponding to the
		 * provided <code>portfolioCode</code>
		 * @param portfolioCode
		 */
		public static void removePortfolio(String portfolioCode) {
			conn = mySQL.connectToSQL();
			int portfolioId = Database.getPortfolioId(portfolioCode, conn);
			PreparedStatement ps =null;
			try {
				ps = conn.prepareStatement("delete from Account where portfolioId =?;");
				ps.setInt(1, portfolioId);
				ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("delete from Portfolio where portfolioId =?;");
				ps.setInt(1, portfolioId);
				ps.executeUpdate();
				ps.close();
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}
		
		/**
		 * Adds a portfolio records to the database with the given data.  If the portfolio has no
		 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
		 * @param portfolioCode
		 * @param ownerCode
		 * @param managerCode
		 * @param beneficiaryCode
		 */
		public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
			conn = mySQL.connectToSQL();
			int ownerId = Database.getPersonId(ownerCode, conn);
			int managerId = Database.getBrokerId((Database.getPersonId(managerCode, conn)),conn);
			int beneficiaryId = Database.getPersonId(beneficiaryCode, conn);
			PreparedStatement ps = null;
			String query = "";
			
			if(beneficiaryId>0) { 
			query += "insert into Portfolio(code,ownerId,managerId,beneficiaryId) values (?,?,?,?);";
			}else {
				query += "insert into Portfolio(code,ownerId,managerId) values (?,?,?);";
			}
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, portfolioCode);
				ps.setInt(2, ownerId);
				ps.setInt(3, managerId);
				if(beneficiaryId >0) {
					ps.setInt(4,beneficiaryId);
				}
				ps.executeUpdate();
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}
		
		/**
		 * Associates the asset record corresponding to <code>assetCode</code> with the 
		 * portfolio corresponding to the provided <code>portfolioCode</code>.  The third 
		 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number of shares</i>
		 * or <i>stake percentage</i> depending on the type of asset the <code>assetCode</code> is
		 * associated with.
		 * @param portfolioCode
		 * @param assetCode
		 * @param value
		 */
		public static void addAsset(String portfolioCode, String assetCode, double value) {
			conn = mySQL.connectToSQL();
			int portfolioId = Database.getPortfolioId(portfolioCode, conn);
			int assetId = Database.getAssetId(assetCode, conn);
			PreparedStatement ps = null;
			ResultSet rs = null;
			String type = "";
			
			try {
				ps = conn.prepareStatement("select type from Asset where assetId =?;");
				ps.setInt(1, assetId);
				rs = ps.executeQuery();
				if(rs.next()) {
					type += rs.getString("type");
				}
				mySQL.clean(null, ps, rs);
				
				ps = conn.prepareStatement("insert into Account(value,assetId,portfolioId) value (?,?,?);");
				if(type.compareTo("Private Investment")==0) {
					ps.setDouble(1, value*100);
				}else {
					ps.setDouble(1,value);
				}
				ps.setInt(2, assetId);
				ps.setInt(3, portfolioId);
				ps.executeUpdate();
				
			}catch (SQLException e) {

				throw new RuntimeException(e);
			}
			mySQL.clean(conn, ps, null);
			return;
		}
		
		
	
}
