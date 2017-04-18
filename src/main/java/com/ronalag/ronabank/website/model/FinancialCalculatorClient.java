package com.ronalag.ronabank.website.model;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class FinancialCalculatorClient extends WebServiceGatewaySupport {

	private static final String CALLBACK_URI = "http://ronalag.com/ronabank/webservice/financialcalculators/getMonthlyPaymentResponse";
	
	private static final String FINANCIAL_CALCULATOR_SERVICE_NAME = "calculator-service";
	
	private static final String INFO_MESSAGE_START = "Requesting monthly mortgage payment with following request info:\n";
	
	private static final String WEB_SERVICE_PATH = "/ws";
	
	private static final Logger log = LoggerFactory.getLogger(FinancialCalculatorClient.class);
		
	public Float getMonthlyPayment(MortgageCalculatorInputBean input, WebServiceTemplate webServiceTemplate, DiscoveryClient discoveryClient) {
		
		if (input == null || webServiceTemplate == null || discoveryClient == null) {
			return null;
		}
		
		// Hack to set the marshaller on the autowired web service template
		WebServiceTemplate wst = getWebServiceTemplate();
		Jaxb2Marshaller marshaller = (Jaxb2Marshaller) wst.getMarshaller();
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
				
		GetMonthlyPaymentRequest request = new GetMonthlyPaymentRequest();
		request.setAmortization(input.getAmortization());
		request.setDownPayment(new BigDecimal(input.getDownPayment()));
		request.setInterestRate(new BigDecimal(input.getInterestRate()));
		request.setPurchasePrice(new BigDecimal(input.getPurchasePrice()));
		
		log.info(INFO_MESSAGE_START + input.toString());
		
		String uri = getURL(discoveryClient); 
		
		if (uri == null) {
			return null;
		}
		
		WebServiceMessageCallback callback = new SoapActionCallback(CALLBACK_URI);
		GetMonthlyPaymentResponse response = (GetMonthlyPaymentResponse)
				webServiceTemplate.marshalSendAndReceive(uri, request, callback);
				
		if (response == null) {
			return null;
		}
		
		BigDecimal monthlyPayment = response.getMonthlyPayment();
		
		return monthlyPayment == null ? null : monthlyPayment.floatValue();
	}
	
	/**
	 * Determines the URL of a financial calculator web service instance.
	 *  
	 * @returns The URL of the web service. 
	 */
	String getURL(DiscoveryClient dc) {	   	
	   	
	   	if (dc == null || dc.getInstances(FINANCIAL_CALCULATOR_SERVICE_NAME) == null) {
	   		return null;
	   	}
	   	
	   	List<ServiceInstance> instances = dc.getInstances(FINANCIAL_CALCULATOR_SERVICE_NAME);
	   	
	   	if (instances == null || instances.isEmpty() || instances.get(0).getUri() == null) {
	   		return null;
	   	}
	   	
	   	return instances.get(0).getUri().toString() + WEB_SERVICE_PATH;
	}
}
