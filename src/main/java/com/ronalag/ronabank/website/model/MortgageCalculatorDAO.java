package com.ronalag.ronabank.website.model;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.ronalag.ronabank.website.model.bean.MortgageCalculatorInputBean;
import com.ronalag.ronabank.website.model.bean.MortgageCalculatorOutputBean;

public class MortgageCalculatorDAO {
	
	public static MortgageCalculatorOutputBean getMortgageCalculatorResult(MortgageCalculatorInputBean input, WebServiceTemplate webServiceTemplate, FinancialCalculatorClient client, DiscoveryClient discoveryClient) {
		
		if (input == null || webServiceTemplate == null || client == null || discoveryClient == null) {
			return null;
		}
		
		MortgageCalculatorOutputBean output = new MortgageCalculatorOutputBean();		
		output.setMonthlyPayment(client.getMonthlyPayment(input, webServiceTemplate));
		
		return output;
	}
}
