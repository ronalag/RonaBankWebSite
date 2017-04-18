package com.ronalag.ronabank.website.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class FinancialCalculatorConfiguration {

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
	public MortgageCalculatorDAO financialCalculatorClient(Jaxb2Marshaller marshaller) {
		MortgageCalculatorDAO client = new MortgageCalculatorDAO();
		client.setDefaultUri(client.getURL());
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
