package com.ronalag.ronabank.website.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Class responsible for configuring the mortgage calculator data access object.
 * 
 * @author Henry Aghaulor
 */
@Configuration
public class MortgageCalculatorDAOConfiguration {

	private static final String CONTEXT_PATH = "com.ronalag.ronabank.webservice.financialcalculators";

	/**
	 * Generates a marshaller.
	 * 
	 * @return A new marshaller object.
	 */
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath(CONTEXT_PATH);
		return marshaller;
	}

	/**
	 * Generates a new mortgage calculator data access object. THe data access object is
	 * derived from a web service gateway support object. This method configures the
	 * marshaller on the gateway.
	 * 
	 * @param marshaller The marshaller that should be used to configure the generated object.
	 * @return THe mortgage calculator data access object.
	 */
	@Bean
	public MortgageCalculatorDAO mortgageCalculatorDAO(Jaxb2Marshaller marshaller) {
		MortgageCalculatorDAO dao = new MortgageCalculatorDAO();
		dao.setDefaultUri(dao.getURL());
		dao.setMarshaller(marshaller);
		dao.setUnmarshaller(marshaller);
		return dao;
	}
}
