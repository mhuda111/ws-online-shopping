package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "CustomerAddress")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class CustAddrRepresentation {
	
	private Integer customerId;
	private String custAddrLine1;	
	private String custAddrLine2;
	private String custCity;
	private String custState;
	private String custZipCode;
	private String custPhone;
	
	//default constructor
	
	public CustAddrRepresentation() {
		this.custAddrLine1 = "";
		this.custAddrLine2 = "";
		this.custCity = "";
		this.custState = "";
		this.custZipCode = "";
		this.custPhone = "";
	}
	
	public CustAddrRepresentation(Integer customerId, String addr1, String addr2, String city, String state, String zip, String phone) {
		this.customerId = customerId;
		this.custAddrLine1 = addr1;
		this.custAddrLine2 = addr2;
		this.custCity = city;
		this.custState = state;
		this.custZipCode = zip;
		this.custPhone = phone;
	}
	
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	public String getCustAddrLine1() {
		return custAddrLine1;
	}
	public void setCustAddrLine1(String custAddrLine1) {
		this.custAddrLine1 = custAddrLine1;
	}
	public String getCustAddrLine2() {
		return custAddrLine2;
	}
	public void setCustAddrLine2(String custAddrLine2) {
		this.custAddrLine2 = custAddrLine2;
	}
	public String getCustCity() {
		return custCity;
	}
	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}
	public String getCustState() {
		return custState;
	}
	public void setCustState(String custState) {
		this.custState = custState;
	}
	public String getCustZipCode() {
		return custZipCode;
	}
	public void setCustZipCode(String custZipCode) {
		this.custZipCode = custZipCode;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
}

