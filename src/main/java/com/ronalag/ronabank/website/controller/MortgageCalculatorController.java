package com.ronalag.ronabank.website.controller;

import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.ronalag.ronabank.website.model.FinancialCalculatorClient;
import com.ronalag.ronabank.website.model.MortgageCalculatorDAO;
import com.ronalag.ronabank.website.model.bean.MortgageCalculatorInputBean;
import com.ronalag.ronabank.website.model.bean.MortgageCalculatorOutputBean;

@Controller
@RequestMapping("/MortgageCalculator")
public class MortgageCalculatorController {

	private static final String AMORTIZATION_ATTRIBUTE = "amortization";
	
	private static final String DECIMAL_ERROR_PART = " must be a decimal value.";
	
	private static final int DEFAULT_AMORTIZATION = 25;
	
	private static final float DEFAULT_DOWN_PAYMENT = (float) 40000.0;
	
	private static final float DEFAULT_INTEREST_RATE = (float) 2.6;
	
	private static final float DEFAULT_PURCHASE_PRICE = (float) 200000.0;
	
	private static final String DOWN_PAYMENT_ATTRIBUTE = "downPayment";
	
	private static final String ERROR_ATTRIBUTE = "errors";
	
	private static final String INTEREST_RATE_ATTRIBUTE = "interestRate";
	
	private static final String PURCHASE_PRICE_ATTRIBUTE = "purchasePrice";
	
	private static final String MORTGAGE_INPUT_ATTRIBUTE = "mortgage";
	
	private static final String MORTGAGE_OUTPUT_ATTRIBUTE = "output";
	
	private static final String MORTGAGE_VIEW = "mortgageCalculator";
	
	private static final String RESET_VALUE = "reset";
	
	private static final String SUBMIT_PARAM = "submit";
	
	private static final String SUBMIT_VALUE = "submit";
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Autowired
	WebServiceTemplate webServiceTemplate;
	
	@Autowired
	FinancialCalculatorClient financialCalculatorClient;
		
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model) {
		model.addAttribute(MORTGAGE_INPUT_ATTRIBUTE, getDefaultMortgageCalculatorInput());
		return MORTGAGE_VIEW;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam Map<String, String> params, Model model) {
		boolean isValid = validateInput(params, model);
						
		if (!isValid || !params.containsKey(SUBMIT_PARAM) || params.get(SUBMIT_PARAM) == null) {
			model.addAttribute(MORTGAGE_INPUT_ATTRIBUTE, getDefaultMortgageCalculatorInput());
			return MORTGAGE_VIEW;
		}		

		MortgageCalculatorInputBean input = getMortgageCalculatorInput(params);
		model.addAttribute(MORTGAGE_INPUT_ATTRIBUTE, input);
		String submit = params.get(SUBMIT_PARAM);
		
		if (submit.equals(SUBMIT_VALUE)) {
			MortgageCalculatorOutputBean output = 
					MortgageCalculatorDAO.getMortgageCalculatorResult(input, this.webServiceTemplate, this.financialCalculatorClient, this.discoveryClient);
			
			if (output != null) {
				model.addAttribute(MORTGAGE_OUTPUT_ATTRIBUTE, output.getMonthlyPayment());
			}
		} else if (submit.equals(RESET_VALUE)) {
			input = getDefaultMortgageCalculatorInput();
		}		

		model.addAttribute(MORTGAGE_INPUT_ATTRIBUTE, input);
		
		return MORTGAGE_VIEW;
	}
	
	private MortgageCalculatorInputBean getDefaultMortgageCalculatorInput() {
		MortgageCalculatorInputBean input = new MortgageCalculatorInputBean();
		input.setAmortization(DEFAULT_AMORTIZATION);
		input.setDownPayment(DEFAULT_DOWN_PAYMENT);
		input.setInterestRate(DEFAULT_INTEREST_RATE);
		input.setPurchasePrice(DEFAULT_PURCHASE_PRICE);
		
		return input;
	}
	
	private Float getFloatValue(String stringValue) {
		Float value;
		
		try {
			value = Float.parseFloat(stringValue);
		} catch (NumberFormatException nfe) {
			value = null;
		}
		
		return value;
	}
	
	private Integer getIntegerValue(String stringValue) {
		Integer value;
		
		try {
			value = Integer.parseInt(stringValue);
		} catch (NumberFormatException nfe) {
			value = null;
		}
		
		return value;
	}
	
	private MortgageCalculatorInputBean getMortgageCalculatorInput(Map<String, String> params) {		
		MortgageCalculatorInputBean input = getDefaultMortgageCalculatorInput();
		
		if (params == null) {
			return input;
		}
		
		if (params.containsKey(AMORTIZATION_ATTRIBUTE)) {
			input.setAmortization(Integer.parseInt(params.get(AMORTIZATION_ATTRIBUTE)));
		}
		
		if (params.containsKey(PURCHASE_PRICE_ATTRIBUTE)) {
			input.setPurchasePrice(Float.parseFloat(params.get(PURCHASE_PRICE_ATTRIBUTE)));
		}
		
		if (params.containsKey(DOWN_PAYMENT_ATTRIBUTE)) {
			input.setDownPayment(Float.parseFloat(params.get(DOWN_PAYMENT_ATTRIBUTE)));
		}
		
		if (params.containsKey(INTEREST_RATE_ATTRIBUTE)) {
			input.setInterestRate(Float.parseFloat(params.get(INTEREST_RATE_ATTRIBUTE)));
		}
		
		return input;
	}
	
	private boolean validateInput(Map<String, String> params, Model model) {
		if (params == null || model == null) {
			return false;
		}
		
		Vector<String> errors = new Vector<String>();
		Integer amortization = null;
		if (params.containsKey(AMORTIZATION_ATTRIBUTE)) {
			amortization = getIntegerValue(params.get(AMORTIZATION_ATTRIBUTE));
		}
				
		if (amortization == null) {
			errors.add("Amortization must be an integer");
		} else if (amortization <= 0 || amortization > 40) {
			errors.add("Amortization must be greater than zero and no greater than forty.");
		}
		
		Float purchasePrice = null;
		if (params.containsKey(PURCHASE_PRICE_ATTRIBUTE)) {
			purchasePrice = getFloatValue(params.get(PURCHASE_PRICE_ATTRIBUTE));
		}
		
		if (purchasePrice == null) {
			errors.addElement("Purchase price " + DECIMAL_ERROR_PART);
		} else if (purchasePrice <= 0){
			errors.add("Purchase price must be greater than zero.");
		}
		
		Float downPayment = null;
		if (params.containsKey(DOWN_PAYMENT_ATTRIBUTE)) {
			downPayment = getFloatValue(params.get(DOWN_PAYMENT_ATTRIBUTE));
		}
		
		if (downPayment == null) {
			errors.add("Down Payment " + DECIMAL_ERROR_PART);
		} else {
			if (downPayment <= 0) {
				errors.add("Down payment must be greater than zero");
			}
			
			if (purchasePrice != null && downPayment >= purchasePrice) {
				errors.add("Down payment should be greater than the purchase price.");
			} 
		}
				
		Float interestRate = null;
		if (params.containsKey(INTEREST_RATE_ATTRIBUTE)) {
			interestRate = getFloatValue(params.get(INTEREST_RATE_ATTRIBUTE));
		}
		
		if (interestRate == null) {
			errors.add("Interest rate must be greater than zero");
		} else {
			if (interestRate <= 0 || interestRate > 100) {
				errors.add("Interest rate should be greater than zero and less than one hundred");
			}			
		}
		
		int length = errors.size();
		
		if (length > 0) {
			model.addAttribute("errors", errors);
		}
		
		return length == 0;
		
	}
}
