package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CreatePortfolio {
	


	/**
	 * This method takes in a file named portfolio.dat and converts the given information into a list of portfolios
	 * 
	 * @return
	 */

	public static List<Portfolio> fileToPortfolio(Map<String,Person> person, Map<String,Asset> assets) {
		List<Portfolio> result = new ArrayList<Portfolio>();
		
		File f = new File("data/Portfolios.dat");
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
			String portfolioTokens[] = line.split(";",-5);
			String portfolioCode = portfolioTokens[0];
			Person owner = person.get(portfolioTokens[1]);    
			Person manager = person.get(portfolioTokens[2]);
			Person beneficiary = null;
			if (portfolioTokens[3].compareTo("") != 0) {
				beneficiary = person.get(portfolioTokens[3]);
			}
			ArrayList<Asset> assetToPortfolio = new ArrayList<Asset>();
			if (portfolioTokens[4].compareTo("") != 0) {
				String assetTokens[] = portfolioTokens[4].split(",");
				for (String tokens : assetTokens) {
					String accountTokens[] = tokens.split(":");
					Asset a = Asset.copyAsset(assets.get(accountTokens[0]),Double.parseDouble(accountTokens[1]));
					assetToPortfolio.add(a);
				}
			}
			Portfolio portfolio =null;
			if(portfolioTokens[3].compareTo("") == 0 & portfolioTokens[4].compareTo("") == 0) {
				portfolio = new Portfolio(portfolioCode,owner,manager);
			}else if(portfolioTokens[3].compareTo("") == 0) {
				portfolio = new Portfolio(portfolioCode,owner,manager,assetToPortfolio);
			}else if(portfolioTokens[4].compareTo("") == 0) {
				portfolio = new Portfolio(portfolioCode,owner,manager,beneficiary);
			}else {
				portfolio = new Portfolio(portfolioCode,owner,manager,beneficiary,assetToPortfolio);
			}
		result.add(portfolio);
		}

		s.close();
		return result;
	}
}
