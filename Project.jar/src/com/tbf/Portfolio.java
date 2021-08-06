package com.tbf;

import java.util.ArrayList;
import java.util.Comparator;

public class Portfolio {

	private String portfolioCode;
	private Person owner;
	private Person manager;
	private Person beneficiary;
	private ArrayList<Asset>assets = new ArrayList<Asset>();
	
	public Portfolio(String portfolioCode, Person owner, Person manager) {
		super();
		this.portfolioCode = portfolioCode;
		this.owner = owner;
		this.manager = manager;
	}

	public Portfolio(String portfolioCode, Person owner, Person manager, Person beneficiary) {
		super();
		this.portfolioCode = portfolioCode;
		this.owner = owner;
		this.manager = manager;
		this.beneficiary = beneficiary;
	}
	
	

	public Portfolio(String portfolioCode, Person owner, Person manager, Person beneficiary,
			ArrayList<Asset> assets) {
		super();
		this.portfolioCode = portfolioCode;
		this.owner = owner;
		this.manager = manager;
		this.beneficiary = beneficiary;
		this.assets = assets;
	}

	public Portfolio(String portfolioCode, Person owner, Person manager,  ArrayList<Asset> assets) {
		super();
		this.portfolioCode = portfolioCode;
		this.owner = owner;
		this.manager = manager;
		this.assets = assets;
	}

	public String getPortfolioCode() {
		return portfolioCode;
	}

	public Person getOwner() {
		return owner;
	}

	public Person getManager() {
		return manager;
	}

	public Person getBeneficiary() {
		return beneficiary;
	}

	public ArrayList<Asset> getAssets() {
		return assets;
	}
	
	public double getTotalfee() {
		return Asset.getFees(this.assets, this.manager);
	}

	public double getTotalCommission() {
		return Asset.getTotalCommision(this.assets, this.manager);
	}
	public double getTotalRisk() {
		return Asset.getTotalRisk(this.assets);
	}
	public double getTotalReturn() {
		return Asset.getTotalReturn(this.assets);
	}
	public double getTotalAmount() {
		return Asset.getTotalValue(this.assets);
	}
	/**
	 * This method sorts the portfolios based on the owners LastName if the the two are 
	 * are equal then compare to firstName name alphabetically.
	 */

	public static Comparator<Portfolio> compareOwner = new Comparator<Portfolio>() {
		public int compare(Portfolio portfolioA, Portfolio portfolioB) {
			return portfolioA.compareToOwner(portfolioB);
		};
	};

	public int compareToOwner(Portfolio portfolio) {
		int compare = this.getOwner().getLastName().compareTo(portfolio.getOwner().getLastName());
		if(compare==0) {
			return this.getOwner().getFirstName().compareTo(portfolio.getOwner().getFirstName());
		}
		return compare;
	}
	/**
	 * This comparator compares each portfolio based on totalValue.
	 * Highest to smallest.
	 */
	
	public static Comparator<Portfolio> compareValue = new Comparator<Portfolio>() {
		public int compare(Portfolio portfolioA, Portfolio portfolioB) {
			return portfolioA.compareToValue(portfolioB);
		}
	};
	
	public int compareToValue(Portfolio portfolio) {
		if(this.getTotalAmount()>portfolio.getTotalAmount()) {
			return -1;
		}else if(this.getTotalAmount()<portfolio.getTotalAmount()) {
			return 1;
		}
		return 0;
	}
	/**
	 * This comparator compares by manager first based on the type of manager Expert/Broker then
	 * compares LastName/FirstName alphabetically.
	 */
	public static Comparator<Portfolio> compareManager = new Comparator<Portfolio>() {
		public int compare(Portfolio portfolioA, Portfolio portfolioB) {
			return portfolioA.compareToManager(portfolioB);
		};
	};

	public int compareToManager(Portfolio portfolio) {
		int compare =0;
		if(this.getManager() instanceof ExpertBroker && portfolio.getManager() instanceof JuniorBroker) {
			compare =-1;
		}else if(this.getManager() instanceof JuniorBroker && portfolio.getManager() instanceof ExpertBroker) {
			compare =1;
		}else {
			compare = 0;
		}
		
		if(compare==0) {
			compare = this.getManager().getLastName().compareTo(portfolio.getManager().getLastName());
		}
		
		if(compare==0) {
			compare = this.getManager().getFirstName().compareTo(portfolio.getManager().getFirstName());
		}
		
		return compare;
	}

}
