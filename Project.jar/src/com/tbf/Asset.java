package com.tbf;

import java.util.List;

public abstract class Asset {
	private String type;
	private String code;
	private String label;
	
	
	public Asset(String type, String code, String label) {
		super();
		this.type = type;
		this.code = code;
		this.label = label;
	}

	
	public String getType() {
		return type;
	}


	public String getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	/**
	 * This method will copy the asset based off the type buy including the value for further calculations
	 * for the asset class.
	 * @param a
	 * @param value
	 * @return
	 */
	
	public static Asset copyAsset(Asset a, double value) {
		
		if(a.getType().compareTo("Private Investment")==0) {
			PrivateInvestment p = new PrivateInvestment((PrivateInvestment) a,value);
			return p;
			
		}else if(a.getType().compareTo("Stock")==0) {
			Stock s = new Stock((Stock) a,value);
			return s;
		}
		
		DepositAccount d = new DepositAccount((DepositAccount) a,value);
		return d;
		
	}
	
	public static double getFees(List<Asset> asset, Person manager) {
		return manager.getfee()*asset.size();
	}
	
	public static double getTotalCommision(List<Asset> asset, Person manager) {
		return getTotalReturn(asset)*manager.getCommision();
	}
	
	
	
	public static double getTotalRisk(List<Asset> asset) {
		double risk = 0.0;
		for(Asset a: asset) {
		risk += (a.getTotalAmount()/getTotalValue(asset))*a.getRisk();
		}
		return risk;
	}
	
	public static double getTotalReturn(List<Asset> asset) {
		double totalReturn = 0.0;
		for(Asset a : asset) {
			totalReturn += a.getAnnualReturn();
		}
		return totalReturn;
	}
	
	public static double getTotalValue(List<Asset> asset) {
		double totalValue =0.0;
		for(Asset a : asset) {
			totalValue += a.getTotalAmount();
		}
		return totalValue;
	}
	

	public abstract double getReturnRate();
	
	public abstract double getRisk();
	
	public abstract double getAnnualReturn();
	
	public abstract double getTotalAmount();

}
