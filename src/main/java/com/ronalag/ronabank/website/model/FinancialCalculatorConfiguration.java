package com.ronalag.ronabank.website.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.ronalag.ronabank.website.common.Common;

@Configuration
public class FinancialCalculatorConfiguration {

	private static final String CONTEXT_PATH = "com.ronalag.ronabank.webservice.financialcalculators";
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath(CONTEXT_PATH);
		return marshaller;
	}

	@Bean
	public FinancialCalculatorClient financialCalculatorClient(Jaxb2Marshaller marshaller) {
		FinancialCalculatorClient client = new FinancialCalculatorClient();
		client.setDefaultUri(Common.WEBSERVICE_URI);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}