package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement(name = "CustomerAddress")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Component
public class CustAddrRepresentation {
	
	private Integer customerId;
	private Integer addrId;
	private String custAddrLine1;	
	private String custAddrLine2;
	private String custCity;
	private String custState;
	private String custZipCode;
	private String message;
	
	//default constructor
	
	public CustAddrRepresentation() {
	}	

	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}
	
	public Integer getAddrId() {
		return this.addrId;
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
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}

}

