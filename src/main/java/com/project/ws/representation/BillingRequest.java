package com.project.ws.representation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
public class BillingRequest {
	
	private Integer custBillId;
	private Integer customerId;
	private String cardNo;
	private String cardType;
	private String cvv;
	private String cardName;
	private Date cardExpiry;
	private String billAddrLine1;
	private String billAddrLine2;
	private String billCity;
	private String billState;
	private String billZipCode;
	
	//default constructor
	
	public BillingRequest() {
	}
	
	
	//setters
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setCustBillId(Integer custBillId) {
		this.custBillId = custBillId;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public void setCVV(String cvv) {
		this.cvv = cvv;
	}
	
	public void setCardExpiry(Date cardExpiry) {
		this.cardExpiry = cardExpiry;
	}
	
	public void setBillAddrLine1(String billAddrLine1) {
		this.billAddrLine1 = billAddrLine1;
	}
	
	public void setBillAddrLine2(String billAddrLine2) {
		this.billAddrLine2 = billAddrLine2;
	}	
	
	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}
	
	public void setBillState(String billState) {
		this.billState = billState;
	}

	public void setBillZipCode(String billZipCode) {
		this.billZipCode = billZipCode;
	}
	
	
	//getters
	public Integer getCustomerId() {
		return this.customerId;
	}

	public Integer getCustBillId() {
		return this.custBillId;
	}
	
	public Date getCardExpiry() {
		return this.cardExpiry;
	}
	
	public String getCardNo() {
		return this.cardNo;
	}

	public String getCardType() {
		return this.cardType;
	}
	
	public String getCardName() {
		return this.cardName;
	}
	
	public String getCVV() {
		return this.cvv;
	}
	
	public String getBillAddrLine1() {
		return this.billAddrLine1;
	}
	
	public String getBillAddrLine2() {
		return this.billAddrLine2;
	}	
	
	public String getBillCity() {
		return this.billCity;
	}
	
	public String getBillState() {
		return this.billState;
	}

	public String getBillZipCode() {
		return this.billZipCode;
	}
}
