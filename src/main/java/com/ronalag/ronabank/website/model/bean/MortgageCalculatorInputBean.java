package com.ronalag.ronabank.website.model.bean;

public class MortgageCalculatorInputBean {	

	private int amortization;
	
	private float downPayment;
	
	private float interestRate;
	
	private float purchasePrice;
	
	public int getAmortization() {
		return this.amortization;
	}	

	public float getDownPayment() {
		return this.downPayment;
	}	

	public float getInterestRate() {
		return this.interestRate;
	}
	
	public float getPurchasePrice() {
		return this.purchasePrice;
	}
	
	public void setAmortization(int amortization) {
		this.amortization = amortization;
	}
	
	public void setDownPayment(float downPayment) {
		this.downPayment = downPayment;
	}
	
	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}
		
	public void setPurchasePrice(float principle) {
		this.purchasePrice = principle;
	}
	
	public String toString() {
		return "Amortization: " + this.amortization + "\n" +
				"Down Payment: " + this.downPayment + "\n" +
				"Interest Rate: " + this.interestRate + "\n" +
				"Purchase Price: " + this.purchasePrice + "\n";
	}
	
}
