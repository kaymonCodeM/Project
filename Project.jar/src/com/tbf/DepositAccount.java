package com.tbf;

public class DepositAccount extends Asset {
	private double apr;
	private double amount;

	public DepositAccount(String type, String code, String label, double apr) {
		super(type, code, label);
		this.apr = apr;
	}
	
	public DepositAccount(DepositAccount depositAccount, double value) {
		super(depositAccount.getType(),depositAccount.getCode(),depositAccount.getLabel());
		this.apr = depositAccount.getApr();
		this.amount = value;
	}
	
	public double getApr() {
		return this.apr;
	}


	
	@Override
	public String toString() {
		return getType() +" " + getCode() + " "+ getLabel() + " " + apr;
	}

	public double getReturnRate() {
		return (getAnnualReturn()/amount);
	}
	
	public double getRisk() {
		return 0.00;
	}
	
	public double getAnnualReturn() {
		return (Math.pow(Math.E, getApr()/100)-1)*amount;
	}
	
	public double getTotalAmount() {
		return amount;
	}


	
	

}
