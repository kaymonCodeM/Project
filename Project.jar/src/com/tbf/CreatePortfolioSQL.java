/**
 * 
 * This file will take a connection to the database and convert the Portfolio Table into a java list made up of
 * multiple portfolios.
 */


package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class CreatePortfolioSQL {
	

	
	/**
	 * This method will take in a map of multiple person and use the integer key to access each person based on the ID information
	 * within the portfolios. This method will need to access the Account and Asset tables to provide access to the asset code
	 * and the value. The method will need to access the broker table to provide information about the manager as well.
	 * To provide all acceptable information of each portfolio the query will left join the Account, Asset, Broker,
	 * and Person tables. 
	 * @param person
	 * @return
	 */
	public static LinkList<Portfolio> databaseToPortfolio(Map<Integer,Person> person, Map<String,Asset> assets){
		
		String query = "	SELECT p.code as portfolioCode," + 
				"		    p.ownerId AS ownerId," + 
				"           p.beneficiaryId AS beneficiaryId," + 
				"           r.personId AS managerId," + 
				"           c.value AS value," + 
				"           a.code AS assetCode from Portfolio p LEFT JOIN Account c ON p.portfolioId = c.portfolioId" + 
				"												 LEFT JOIN Asset a ON c.assetId = a.assetId" + 
				"                                                LEFT JOIN Broker b ON p.managerId =b.brokerId" + 
				"                                                LEFT JOIN Person r ON b.brokerId = r.brokerId WHERE p.portfolioId = ?;";
		
		LinkList<Portfolio> result = new LinkList<Portfolio>();
		Connection conn = mySQL.connectToSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int size = MaxSQL.numberOfPortfolio(conn);
		try {
			ps = conn.prepareStatement(query);
			for(int portfolioId = 1;portfolioId<size+1;portfolioId++) {
			ps.setInt(1,  portfolioId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String portfolioCode = rs.getString("portfolioCode");
				Person owner = person.get(rs.getInt("ownerId"));
				Person manager = person.get(rs.getInt("managerId"));
				Person beneficiary = person.get(rs.getInt("beneficiaryId"));
				
				Portfolio portfolio =null;
				if(rs.getString("assetCode")!=null) {
					ArrayList<Asset> assetToPortfolio = new ArrayList<Asset>();
					assetToPortfolio.add(Asset.copyAsset(assets.get(rs.getString("assetCode")), rs.getDouble("value")));
	
				while(rs.next()) {
					assetToPortfolio.add(Asset.copyAsset(assets.get(rs.getString("assetCode")), rs.getDouble("value")));
					
				}
				
				if(beneficiary==null) {
				portfolio = new Portfolio(portfolioCode,owner,manager,assetToPortfolio);
				}else {
				portfolio = new Portfolio(portfolioCode,owner,manager,beneficiary,assetToPortfolio);
				}
				}else {
					if(beneficiary==null) {
						portfolio = new Portfolio(portfolioCode,owner,manager);
					}else {
						portfolio = new Portfolio(portfolioCode,owner,manager,beneficiary);
					}
				}
				result.addSort(portfolio,Portfolio.compareOwner);
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
