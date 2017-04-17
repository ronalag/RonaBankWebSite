package com.ronalag.ronabank.website.controller;

import java.util.Map;

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
	
	private static final String DOWN_PAYMENT_ATTRIBUTE = "downPayment";
	
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
		model.addAttribute(MORTGAGE_INPUT_ATTRIBUTE, new MortgageCalculatorInputBean());
		return MORTGAGE_VIEW;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam Map<String, String> params, Model model) {
		MortgageCalculatorInputBean input = getMortgageCalculatorInput(params);
		model.addAttribute(MORTGAGE_INPUT_ATTRIBUTE, input);
		
		if (params == null || !params.containsKey(SUBMIT_PARAM) || params.get(SUBMIT_PARAM) == null) {
			return MORTGAGE_VIEW;
		}
		
		String submit = params.get(SUBMIT_PARAM);
				
		if (submit.equals(SUBMIT_VALUE)) {
			MortgageCalculatorOutputBean output =
					MortgageCalculatorDAO.getMortgageCalculatorResult(input, this.webServiceTemplate, this.financialCalculatorClient, this.discoveryClient);
			
			if (output == null) {
				
			} else {
			float monthlyPayment = output.getMonthlyPayment();
			 model.addAttribute(MORTGAGE_OUTPUT_ATTRIBUTE, output.getMonthlyPayment());
			}
		} else if (submit.equals(RESET_VALUE)) {
			
		}
		return MORTGAGE_VIEW;
	}
	
	private MortgageCalculatorInputBean getMortgageCalculatorInput(Map<String, String> params) {		
		MortgageCalculatorInputBean input = new MortgageCalculatorInputBean();
		
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
}
