package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateAsset {
	/**
	 * This method takes in a file called Asset.dat and converts them into the Asset
	 * class. A Asset can be a Deposit Account, Stocks, or Private Investment.
	 * Deposit Account will have a code id, a type, label and apr. Stocks will have
	 * a code id, type, label, quarterlyDividend, baseRateOfReturn;betaMeasure,
	 * stockSymbol, and sharePrice. Private Investment will have a code id, label,
	 * quarterlyDividend, basedRateOfReturn, baseOmegaMeasure, and a totalValue.
	 * 
	 * @return
	 */
	public static Map<String,Asset> fileToAssets() {
		Map<String,Asset> result = new HashMap<String,Asset>();
		File f = new File("data/Assets.dat");
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		String line = s.nextLine();
		int numberOfLines = Integer.parseInt(line);
		for (int i = 0; i < numberOfLines; i++) {
			line = s.nextLine();
			String assetTokens[] = line.split(";");
			String type = "";
			String code = assetTokens[0];
			String label = assetTokens[2];
			if (assetTokens[1].compareTo("D") == 0) {
				type += "Deposit Account";
				double apr = Double.parseDouble(assetTokens[3]);
				DepositAccount deposit = new DepositAccount(type, code, label, apr);
				result.put(code,deposit);
			} else if (assetTokens[1].compareTo("S") == 0) {
				type += "Stock";
				double quarterlyDividend = Double.parseDouble(assetTokens[3]);
				double baseRateOfReturn = Double.parseDouble(assetTokens[4]);
				double betaMeasure = Double.parseDouble(assetTokens[5]);
				String stockSymbol = assetTokens[6];
				double sharePrice = Double.parseDouble(assetTokens[7]);
				Stock stock = new Stock(type, code, label, quarterlyDividend, baseRateOfReturn, betaMeasure,
						stockSymbol, sharePrice);
				result.put(code,stock);
			} else {
				type += "Private Investment";
				double quarterlyDividend = Double.parseDouble(assetTokens[3]);
				double baseRateOfReturn = Double.parseDouble(assetTokens[4]);
				double baseOmegaMeasure = Double.parseDouble(assetTokens[5]);
				double totalValue = Double.parseDouble(assetTokens[6]);
				PrivateInvestment privateInvestment = new PrivateInvestment(type, code, label, quarterlyDividend,
						baseRateOfReturn, baseOmegaMeasure, totalValue);
				result.put(code,privateInvestment);
			}

		}

		s.close();
		return result;
	}
}
