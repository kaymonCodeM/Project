package com.tbf;

public class PrivateInvestment extends Asset{
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double baseOmegaMeasure;
	private double totalValue;
	private double percentOfOwned;
	
	public PrivateInvestment(String type, String code, String label, double quarterlyDividend, double baseRateOfReturn,
			double baseOmegaMeasure, double totalValue) {
		super(type, code, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.baseOmegaMeasure = baseOmegaMeasure;
		this.totalValue = totalValue;
	}
	
	public PrivateInvestment(PrivateInvestment privateInvestment, double percentOfOwned) {
		super(privateInvestment.getType(), privateInvestment.getCode(), privateInvestment.getLabel());
		this.quarterlyDividend = privateInvestment.getQuarterlyDividend();
		this.baseRateOfReturn = privateInvestment.getBaseRateOfReturn();
		this.baseOmegaMeasure = privateInvestment.getBaseOmegaMeasure();
		this.totalValue = privateInvestment.getTotalValue();
		this.percentOfOwned = percentOfOwned;
	}


	public double getQuarterlyDividend() {
		return this.quarterlyDividend;
	}

	public double getBaseRateOfReturn() {
		return this.baseRateOfReturn;
	}

	public double getBaseOmegaMeasure() {
		return this.baseOmegaMeasure;
	}

	public double getTotalValue() {
		return this.totalValue;
	}
	
	
	public double getReturnRate() {
		return getAnnualReturn()/getTotalAmount();
	}
	
	public double getRisk() {
		return Math.pow(Math.E, (-125500/this.totalValue))+(this.baseOmegaMeasure);
	}
	
	public double getAnnualReturn() {
		return (((this.baseRateOfReturn/100.0)*this.totalValue)+(4*this.quarterlyDividend))*(this.percentOfOwned/100.0);
	}
	
	public double getTotalAmount() {
		return this.totalValue*(this.percentOfOwned/100.00);
	}

	
	
	

}
