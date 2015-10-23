package com.project.ws.representation;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CustomerBillingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class CustBillingRepresentation {
	
	private Integer custBillId;
	private Integer customerId;
	private String cardNo;
	private String cardType;
	private String cardName;
	private Date cardExpiry;
	private String billAddrLine1;
	private String billAddrLine2;
	private String billCity;
	private String billState;
	private String billZipCode;
	//default constructor
	
	public CustBillingRepresentation() {
		this.cardNo = "";
		this.cardType = "";
		this.cardName = "";
		this.billAddrLine1 = "";
		this.billAddrLine2 = "";
		this.billCity = "";
		this.billState = "";
		this.billZipCode = "";
	}
	
	public CustBillingRepresentation(Integer customerId, String cardType, String cardNo, String cvv, Date expiry, String cardName, String addr1, String addr2, String city, String state, String zip) {
		this.customerId = customerId;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.cardExpiry = expiry;
		this.cardName = cardName;
		this.billAddrLine1 = addr1;
		this.billAddrLine2 = addr2;
		this.billCity = city;
		this.billState = state;
		this.billZipCode = zip;
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
