package com.ronalag.ronabank.website.model;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.ronalag.ronabank.website.model.bean.MortgageCalculatorInputBean;
import com.ronalag.ronabank.website.model.bean.MortgageCalculatorOutputBean;

/**
 * The Mortgage Calculator Data Access Object(DAO). Encapsulates the logic for connecting to the SOAP web service.
 * 
 * @author Henry Aghaulor
 */
public class MortgageCalculatorDAO {
	
	/**
	 * Sends the monthly mortgage payment to the SOAP web service and gets the result.
	 * 
	 * @param input The input fields of the monthly mortgage payment calculator required by the web service.
	 * @param webServiceTemplate A component used to send and receive messages to the web service.
	 * @param client
	 * @param discoveryClient
	 * @return
	 */
	public static MortgageCalculatorOutputBean getMortgageCalculatorResult(MortgageCalculatorInputBean input, WebServiceTemplate webServiceTemplate, FinancialCalculatorClient client, DiscoveryClient discoveryClient) {
		
		if (input == null || webServiceTemplate == null || client == null || discoveryClient == null) {
			return null;
		}
				
		MortgageCalculatorOutputBean output = new MortgageCalculatorOutputBean();		
		output.setMonthlyPayment(client.getMonthlyPayment(input, webServiceTemplate, discoveryClient));
		
		return output;
	}
}
