package com.ronalag.ronabank.website.model;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.ronalag.ronabank.webservice.financialcalculators.GetMonthlyPaymentRequest;
import com.ronalag.ronabank.webservice.financialcalculators.GetMonthlyPaymentResponse;
import com.ronalag.ronabank.website.model.bean.MortgageCalculatorInputBean;
import com.ronalag.ronabank.website.model.bean.MortgageCalculatorOutputBean;

/**
 * The Mortgage Calculator Data Access Object(DAO). Encapsulates the logic for connecting to the SOAP web service.
 * 
 * @author Henry Aghaulor
 */
public class MortgageCalculatorDAO extends WebServiceGatewaySupport {
	
	private static final String CALLBACK_URI = "http://ronalag.com/ronabank/webservice/financialcalculators/getMonthlyPaymentResponse";
	
	private static final String FINANCIAL_CALCULATOR_SERVICE_NAME = "calculator-service";
	
	private static final String INFO_MESSAGE_START = "Requesting monthly mortgage payment with following request info:\n";
	
	private static final String WEB_SERVICE_PATH = "/ws";
	
	private static final Logger log = LoggerFactory.getLogger(FinancialCalculatorClient.class);
	
	@Autowired
	DiscoveryClient discoveryClient;
		
	public Float getMonthlyPayment(MortgageCalculatorInputBean input) {
		
		if (input == null) {
			return null;
		}
		
		GetMonthlyPaymentRequest request = new GetMonthlyPaymentRequest();
		request.setAmortization(input.getAmortization());
		request.setDownPayment(new BigDecimal(input.getDownPayment()));
		request.setInterestRate(new BigDecimal(input.getInterestRate()));
		request.setPurchasePrice(new BigDecimal(input.getPurchasePrice()));
		
		log.info(INFO_MESSAGE_START + input.toString());
		
		String uri = this.getURL(); 
		
		if (uri == null) {
			return null;
		}
		
		WebServiceMessageCallback callback = new SoapActionCallback(CALLBACK_URI);
		GetMonthlyPaymentResponse response = (GetMonthlyPaymentResponse)
				this.getWebServiceTemplate().marshalSendAndReceive(uri, request, callback);
				
		if (response == null) {
			return null;
		}
		
		BigDecimal monthlyPayment = response.getMonthlyPayment();
		
		return monthlyPayment == null ? null : monthlyPayment.floatValue();
	}
	
	
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
	
	/**
	 * Determines the URL of a financial calculator web service instance.
	 *  
	 * @returns The URL of the web service. 
	 */
	String getURL() {	   	
	   	
	   	if (this.discoveryClient == null || 
	   			this.discoveryClient.getInstances(FINANCIAL_CALCULATOR_SERVICE_NAME) == null) {
	   		return null;
	   	}
	   	
	   	List<ServiceInstance> instances = 
	   			this.discoveryClient.getInstances(FINANCIAL_CALCULATOR_SERVICE_NAME);
	   	
	   	if (instances == null || instances.isEmpty() || instances.get(0).getUri() == null) {
	   		return null;
	   	}
	   	
	   	return instances.get(0).getUri().toString() + WEB_SERVICE_PATH;
	}
}
