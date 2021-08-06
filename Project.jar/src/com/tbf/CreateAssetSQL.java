/**
 *  This file will access the database and convert each asset 
 *  table into a map in java with the code as the key.
 */

package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreateAssetSQL {
	/**
	 * This method will access the database followed by a query that will access the asset table followed by left joins
	 * to the PrivateInvestmen, Stock, and DepositeAccount tables.
	 * The method will access the Asset class and create a asset based on its type and store the result into a map.
	 * The code of each asset will be the key to the particular asset.
	 * The return value will be the map of all the asset results.
	 * @return
	 */
	
	public static Map<String,Asset> databaseToAsset(){
		String query = "SELECT a.code AS code," + 
				"	    a.type AS type," + 
				"       a.label AS label," + 
				"       s.quarterlyDividend AS quarterlyDividendStock," + 
				"       s.baseRateOfReturn AS baseRateOfReturnStock," + 
				"       s.betaMeasure AS betaMeasure," + 
				"       s.stockSymbol AS stockSymbol," + 
				"       s.sharePrice AS sharePrice," + 
				"       d.apr AS apr," + 
				"       p.quarterlyDividend AS quarterlyDividendPrivate," + 
				"       p.baseRateOfReturn AS baseRateOfReturnPrivate," + 
				"       p.baseOmegaMeasure AS baseOmegaMeasure," + 
				"       p.totalValue AS totalValue FROM Asset a LEFT JOIN PrivateInvestment p ON a.assetId = p.assetId" + 
				"											    LEFT JOIN Stock s ON s.assetId = a.assetId" + 
				"											    LEFT JOIN DepositAccount d ON a.assetId = d.assetId;";
		
		Connection conn = mySQL.connectToSQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,Asset> result = new HashMap<String,Asset>();

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				
				String type = rs.getString("type");
				String code = rs.getString("code");
				String label = rs.getString("label");
				if (type.compareTo("Deposit Account") == 0) {
					double apr = rs.getDouble("apr");
					DepositAccount deposit = new DepositAccount(type, code, label, apr);
					result.put(code,deposit);
				} else if (type.compareTo("Stock") == 0) {
					double quarterlyDividend = rs.getDouble("quarterlyDividendStock");
					double baseRateOfReturn = rs.getDouble("baseRateOfReturnStock");
					double betaMeasure = rs.getDouble("betaMeasure");
					String stockSymbol = rs.getString("stockSymbol");
					double sharePrice = rs.getDouble("sharePrice");
					Stock stock = new Stock(type, code, label, quarterlyDividend, baseRateOfReturn, betaMeasure,
							stockSymbol, sharePrice);
					result.put(code,stock);
				} else {
					double quarterlyDividend = rs.getDouble("quarterlyDividendPrivate");
					double baseRateOfReturn = rs.getDouble("baseRateOfReturnPrivate");
					double baseOmegaMeasure = rs.getDouble("baseOmegaMeasure");
					double totalValue = rs.getDouble("totalValue");
					PrivateInvestment privateInvestment = new PrivateInvestment(type, code, label, quarterlyDividend,
							baseRateOfReturn, baseOmegaMeasure, totalValue);
					result.put(code,privateInvestment);
			}
		}
		} catch (SQLException e) {

			throw new RuntimeException(e);
		}

		
		mySQL.clean(conn, ps, rs);
		return result;
	}

}
