package com.ronalag.ronabank.website.model.bean;

/**
 * 
 * This bean contains all the fields from the output of the monthly mortgage
 * payment calculation of the RonaBank webservice.
 * 
 * @author Henry Aghaulor
 */
public class MortgageCalculatorOutputBean {
	private float monthlyPayment;

	/**
	 * Gets the monthly payment.
	 * 
	 * @return The monthly payment.
	 */
	public float getMonthlyPayment() {
		return monthlyPayment;
	}

	/**
	 * Sets the monthly payment.
	 * 
	 * @param monthlyPayment The monthly payment.
	 */
	public void setMonthlyPayment(float monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
}
