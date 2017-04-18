package com.ronalag.ronabank.website.model.bean;

/**
 * 
 * This bean contains all the fields from the mortgage calculator
 * @author Henry Aghaulor
 *
 */
public class MortgageCalculatorInputBean {	

	private int amortization;
	
	private float downPayment;
	
	private float interestRate;
	
	private float purchasePrice;
	
	/**
	 * Gets the amortization of the mortgage.
	 * 
	 * @return The amortization of the mortgage.
	 */
	public int getAmortization() {
		return this.amortization;
	}	

	/**
	 * Gets the amortization of the mortgage.
	 * 
	 * @return The amortization of the mortgage.
	 */
	public float getDownPayment() {
		return this.downPayment;
	}	

	/**
	 * Gets the interest rate of the mortgage.
	 * 
	 * @return The interest rate of the mortgage.
	 */
	public float getInterestRate() {
		return this.interestRate;
	}

	/**
	 * Gets the purchase price of the mortgage.
	 * 
	 * @return The purchase price of the mortgage.
	 */
	public float getPurchasePrice() {
		return this.purchasePrice;
	}

	/**
	 * Sets the amortization of the mortgage.
	 * 
	 * @param amortization The amortization of the mortgage.
	 */
	public void setAmortization(int amortization) {
		this.amortization = amortization;
	}

	/**
	 * Sets the down payment of the mortgage.
	 * 
	 * @param downPayment The down payment of the mortgage.
	 */
	public void setDownPayment(float downPayment) {
		this.downPayment = downPayment;
	}

	/**
	 * Sets the interest rate of the mortgage.
	 * 
	 * @param interestRate The interest rate of the mortgage.
	 */
	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * Sets the purchase price of the mortgage.
	 * 
	 * @param principle The purchase price of the mortgage.
	 */
	public void setPurchasePrice(float principle) {
		this.purchasePrice = principle;
	}

	/**
	 * Gets a string representation of this object.
	 * 
	 * @return a string representation of this object.
	 */
	public String toString() {
		return "Amortization: " + this.amortization + "\n" +
				"Down Payment: " + this.downPayment + "\n" +
				"Interest Rate: " + this.interestRate + "\n" +
				"Purchase Price: " + this.purchasePrice + "\n";
	}
	
}
