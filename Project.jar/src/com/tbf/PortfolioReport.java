
package com.tbf;


import java.util.Map;
/**
 * This file is used to access and execute the given classes to provide a report over
 * each portfolio. To create a report a given list of portfolios and a map of assets must
 * be provided. the printRecords() method will output a report over the assets given by the
 * portfolio asset codes.
 */
public class PortfolioReport {
	
	public static void main(String args[]){
		
		Map<Integer,Person> person = CreatePersonSQL.databaseToPerson();
		Map<String,Asset> assets= CreateAssetSQL.databaseToAsset();
		LinkList<Portfolio> ownerSort = CreatePortfolioSQL.databaseToPortfolio(person,assets);
		LinkList<Portfolio> managerSort = ownerSort.sortList(Portfolio.compareManager);
		LinkList<Portfolio>valueSort = ownerSort.sortList(Portfolio.compareValue);
		System.out.println("\n\n\n" +"Owner Sort Report");
		Report.printNoDetail(ownerSort);
		System.out.println("\n\n\n" +"Value Sort Report");
		Report.printNoDetail(valueSort);
		System.out.println("\n\n\n" +"Manager Sort Report");
		Report.printNoDetail(managerSort);
		
		
	}

}
 