package com.project.ws.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerBillingDetailsBO {
	
	private long customerBillId;
	private long customerID;
	private String billingAddressLine1;
	private String billingAddressLine2;
	private String billCity;
	private String billState;
	private long zipCode;
	private String cardName;
	
	
	public long getCustomerBillId() {
		return customerBillId;
	}
	public void setCustomerBillId(long customerBillId) {
		this.customerBillId = customerBillId;
	}
	public long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
	public String getBillingAddressLine1() {
		return billingAddressLine1;
	}
	public void setBillingAddressLine1(String billingAddressLine1) {
		this.billingAddressLine1 = billingAddressLine1;
	}
	public String getBillingAddressLine2() {
		return billingAddressLine2;
	}
	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}
	public String getBillCity() {
		return billCity;
	}
	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}
	public String getBillState() {
		return billState;
	}
	public void setBillState(String billState) {
		this.billState = billState;
	}
	public long getZipCode() {
		return zipCode;
	}
	public void setZipCode(long zipCode) {
		this.zipCode = zipCode;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	
	

}
