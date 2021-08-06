package com.tbf;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

public class XML {
	public static void toXML(Map<Integer,Person> person,Map<String,Asset> assets, LinkList<Portfolio> portfolio) throws IOException {
	XStream xstream = new XStream();
	FileWriter filePerson = new FileWriter("data/Persons.xml");
	FileWriter fileAssets = new FileWriter("data/Assets.xml");
	FileWriter filePortfolio = new FileWriter("data/Portfolio.xml");
	
	for (Integer p : person.keySet()) {
		xstream.alias("Person", Person.class);
		String xml = xstream.toXML(person.get(p));
			filePerson.write(xml);
	}
	
	for (String a : assets.keySet()) {
		xstream.alias("Asset", Asset.class);
		String xml = xstream.toXML(assets.get(a));
			fileAssets.write(xml);
	}
	for (Portfolio pp : portfolio) {
		xstream.alias("Portfolio", Portfolio.class);
		String xml = xstream.toXML(pp);
			filePortfolio.write(xml);
	}
	
	filePortfolio.close();
	fileAssets.close();
	filePerson.close();
}
}
