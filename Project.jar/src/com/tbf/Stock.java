package com.tbf;

public class Stock extends Asset{
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double betaMeasure;
	private String stockSymbol;
	private double sharePrice;
	private double numberOfStocks;
	
	public Stock(String type, String code, String label, double quarterlyDividend, double baseRateOfReturn,
			double betaMeasure, String stockSymbol, double sharePrice) {
		super(type, code, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaMeasure = betaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
	}
	public Stock(Stock stock, double numberOfStocks) {
			super(stock.getType(), stock.getCode(), stock.getLabel());
			this.quarterlyDividend = stock.getQuarterlyDividend();
			this.baseRateOfReturn = stock.getBaseRateOfReturn();
			this.betaMeasure = stock.getBetaMeasure();
			this.stockSymbol = stock.getStockSymbol();
			this.sharePrice = stock.getSharePrice();
			this.numberOfStocks = numberOfStocks;
	}

	public double getQuarterlyDividend() {
		return this.quarterlyDividend;
	}

	public double getBaseRateOfReturn() {
		return this.baseRateOfReturn;
	}

	public double getBetaMeasure() {
		return this.betaMeasure;
	}

	public String getStockSymbol() {
		return this.stockSymbol;
	}

	public double getSharePrice() {
		return this.sharePrice;
	}
	
	public double getReturnRate() {
		return getAnnualReturn()/getTotalAmount();
	}
	
	public double getRisk() {
		return this.betaMeasure;
	}
	
	public double getAnnualReturn() {
		return ((this.baseRateOfReturn/100.0)*this.sharePrice*numberOfStocks)+(4*this.quarterlyDividend*numberOfStocks);
	}
	
	public double getTotalAmount() {
		return this.sharePrice*numberOfStocks;
	}

	
	

}
