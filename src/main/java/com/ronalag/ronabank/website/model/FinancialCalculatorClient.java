package com.ronalag.ronabank.website.model;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.ronalag.ronabank.webservice.financialcalculators.GetMonthlyPaymentRequest;
import com.ronalag.ronabank.webservice.financialcalculators.GetMonthlyPaymentResponse;
import com.ronalag.ronabank.website.common.Common;
import com.ronalag.ronabank.website.model.bean.MortgageCalculatorInputBean;

public class FinancialCalculatorClient extends WebServiceGatewaySupport {

	private static final String CALLBACK_URI = "http://ronalag.com/ronabank/webservice/financialcalculators/getMonthlyPaymentResponse";
	
	private static final String INFO_MESSAGE_START = "Requesting monthly mortgage payment with following request info:\n";
	
	private static final Logger log = LoggerFactory.getLogger(FinancialCalculatorClient.class);
		
	public Float getMonthlyPayment(MortgageCalculatorInputBean input, WebServiceTemplate webServiceTemplate) {
		
		if (input == null || webServiceTemplate == null) {
			return null;
		}
		
		GetMonthlyPaymentRequest request = new GetMonthlyPaymentRequest();
		request.setAmortization(input.getAmortization());
		request.setDownPayment(new BigDecimal(input.getDownPayment()));
		request.setInterestRate(new BigDecimal(input.getInterestRate()));
		request.setPurchasePrice(new BigDecimal(input.getPurchasePrice()));
		
		log.info(INFO_MESSAGE_START + input.toString());
		
		String uri = Common.WEBSERVICE_URI;
		WebServiceMessageCallback callback = new SoapActionCallback(CALLBACK_URI);
		WebServiceTemplate wst = getWebServiceTemplate();
		GetMonthlyPaymentResponse response = (GetMonthlyPaymentResponse)
				wst.marshalSendAndReceive(uri, request, callback);
				
		if (response == null) {
			return null;
		}
		
		BigDecimal monthlyPayment = response.getMonthlyPayment();
		
		return monthlyPayment == null ? null : monthlyPayment.floatValue();
	}
}
