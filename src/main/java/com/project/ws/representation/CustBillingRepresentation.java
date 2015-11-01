package com.project.ws.representation;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement(name = "CustomerBillingDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Component
public class CustBillingRepresentation {
	
	private Integer custBillId;
	private Integer customerId;
	private String cardNo;
	private String cardType;
	private String billAddrLine1;
	private String billAddrLine2;
	private String billCity;
	private String billState;
	private String billZipCode;
	
	public CustBillingRepresentation() {
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
	
	public String getCardNo() {
		return this.cardNo;
	}

	public String getCardType() {
		return this.cardType;
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
