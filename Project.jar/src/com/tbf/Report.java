package com.tbf;

import java.util.ArrayList;
public class Report {
	public static void printNoDetail(LinkList<Portfolio> portfolio) {
		System.out.println("Portfolio Summary Report");
		System.out.println("=========================================="
				+ "==================================================="
				+ "===================================================");
		System.out.printf("%-10s%-20s%-20s%15s%20s%20s%15s%17s\n", "Portfolio",
																  "Owner",
																  "Manager",
																  "Fees",
																  "Commisions",
																  "Weighted Risk",
																  "Return",
																  "Total");
		double totalFee= 0.0;
		double totalCommisions =0.0;
		double totalReturn=0.0;
		double totalAmount=0.0;
		for(Portfolio p: portfolio) {
			System.out.printf("%-10s",p.getPortfolioCode());
			System.out.printf("%-20s", p.getOwner().getName());
			System.out.printf("%-20s", p.getManager().getName());
			
			if(p.getAssets()!=null) {
				double fee = p.getTotalfee();
				double commission = p.getTotalCommission();
				double risk = p.getTotalRisk();
				double annualReturn = p.getTotalReturn();
				double totalValue = p.getTotalAmount();
				System.out.printf("  $");
				System.out.printf("%15.2f",fee);
				System.out.printf("  $");
				System.out.printf("%15.2f",commission);
				System.out.printf("  $");
				System.out.printf("%15.4f",risk);
				System.out.printf("  $");
				System.out.printf("%15.2f",annualReturn);
				System.out.printf("  $");
				System.out.printf("%15.2f\n",totalValue);
				totalFee+=fee;
				totalCommisions +=commission;
				totalReturn+=annualReturn;
				totalAmount+=totalValue;
			}else {
				System.out.printf("  $");
				System.out.printf("%15.2f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.4f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.2f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.2f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.2f\n",0.00);
			}
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%50s  $%15.2f  $%15.2f   %15s  $%15.2f  $%15.2f " , "Total",
				   									  totalFee,
				   									  totalCommisions,
				   									  " ",
				   									  totalReturn,
				   									  totalAmount);
	}


	public static void printRecords(LinkList<Portfolio> portfolio) {
		System.out.println("Portfolio Summary Report");
		System.out.println("=========================================="
				+ "==================================================="
				+ "===================================================");
		System.out.printf("%-10s%-20s%-20s%15s%20s%20s%15s%17s\n", "Portfolio",
																  "Owner",
																  "Manager",
																  "Fees",
																  "Commisions",
																  "Weighted Risk",
																  "Return",
																  "Total");
		double totalFee= 0.0;
		double totalCommisions =0.0;
		double totalReturn=0.0;
		double totalAmount=0.0;
		for(Portfolio p: portfolio) {
			System.out.printf("%-10s",p.getPortfolioCode());
			System.out.printf("%-20s", p.getOwner().getName());
			System.out.printf("%-20s", p.getManager().getName());
			
			if(p.getAssets()!=null) {
				double fee = p.getTotalfee();
				double commission = p.getTotalCommission();
				double risk = p.getTotalRisk();
				double annualReturn = p.getTotalReturn();
				double totalValue = p.getTotalAmount();
				System.out.printf("  $");
				System.out.printf("%15.2f",fee);
				System.out.printf("  $");
				System.out.printf("%15.2f",commission);
				System.out.printf("  $");
				System.out.printf("%15.4f",risk);
				System.out.printf("  $");
				System.out.printf("%15.2f",annualReturn);
				System.out.printf("  $");
				System.out.printf("%15.2f\n",totalValue);
				totalFee+=fee;
				totalCommisions +=commission;
				totalReturn+=annualReturn;
				totalAmount+=totalValue;
			}else {
				System.out.printf("  $");
				System.out.printf("%15.2f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.4f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.2f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.2f",0.00);
				System.out.printf("  $");
				System.out.printf("%15.2f\n",0.00);
			}
		}
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%50s  $%15.2f  $%15.2f   %15s  $%15.2f  $%15.2f " , "Total",
				   									  totalFee,
				   									  totalCommisions,
				   									  " ",
				   									  totalReturn,
				   									  totalAmount);
		for(Portfolio p : portfolio) {
			System.out.println("\n"+"Portfolio "+ p.getPortfolioCode());
			System.out.println("-----------------------------------------------");
			System.out.println("Owner:");
			System.out.println(p.getOwner().getName());
			ArrayList<String> emails = p.getOwner().getEmail();
			if(emails!=null) {
				for(String e : emails) {
					System.out.println(e);
				}
			}
			System.out.println(p.getOwner().getAddress().getStreet());
			System.out.printf("%s, %s %s %s\n",p.getOwner().getAddress().getCity(),
								      		   p.getOwner().getAddress().getState(),
								      		   p.getOwner().getAddress().getCountry(),
								      		   p.getOwner().getAddress().getZip());
			System.out.println("Manager:");
			System.out.println(p.getManager().getName());
			Person beneficiary = p.getBeneficiary();
			if(beneficiary!=null) {
				System.out.println("Beneficiary:");
				System.out.println(beneficiary.getName());
				System.out.println(beneficiary.getAddress().getStreet());
				System.out.printf("%s, %s %s %s\n", beneficiary.getAddress().getCity(),
												 beneficiary.getAddress().getState(),
									  			 beneficiary.getAddress().getCountry(),
									  			 beneficiary.getAddress().getZip());
			}
	
			if(p.getAssets()!=null) {
				System.out.println("Assets:");
				System.out.printf("%-10s %-45s %-15s %-20s %-20s %-15s\n","Code","Assets","Return Rate%","Risk","Annual Return","Value");
				for(Asset a : p.getAssets()) {
					System.out.printf("%-10s %-45s %-15.2f %-20.4f $ %-15.2f $ %-15.2f\n", a.getCode(),
																					 a.getLabel(),
																					 a.getReturnRate()*100.0,
																					 a.getRisk(),
																					 a.getAnnualReturn(),
																					 a.getTotalAmount());
				}
				System.out.println("--------------------------------------------------------------------------------------------------------------------------");
				System.out.printf("%62s %15.4f $ %20.2f $ %17.2f", "Total",
																	p.getTotalRisk(),
																	p.getTotalReturn(),
																	p.getTotalAmount());
			}
			
		}
		
	}

}
