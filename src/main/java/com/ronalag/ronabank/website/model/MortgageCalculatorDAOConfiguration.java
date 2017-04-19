package com.ronalag.ronabank.website.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class MortgageCalculatorDAOConfiguration {

	private static final String CONTEXT_PATH = "com.ronalag.ronabank.webservice.financialcalculators";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath(CONTEXT_PATH);
		return marshaller;
	}

	@Bean
	public MortgageCalculatorDAO mortgageCalculatorDAO(Jaxb2Marshaller marshaller) {
		MortgageCalculatorDAO dao = new MortgageCalculatorDAO();
		dao.setDefaultUri(dao.getURL());
		dao.setMarshaller(marshaller);
		dao.setUnmarshaller(marshaller);
		return dao;
	}
}
