package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Component
public class VendorRequest {
	
	private Integer vendorId;
	private String vendorName;
	private String vendorAddrLine1;
	private String vendorAddrLine2;
	private String vendorCity;
	private String vendorState;
	private String vendorZipCode;
	private String vendorCountry;
	
	public VendorRequest() {
		this.vendorName = "";
		this.vendorAddrLine1 = "";
		this.vendorAddrLine2 = "";
		this.vendorCity = "";
		this.vendorState = "";
		this.vendorZipCode = "";
		this.vendorCountry = "";
	}
	
	public VendorRequest(String name, String addr1, String addr2, String city, String state, String country, String zip) {
		
		this.vendorName = name;
		this.vendorAddrLine1 = addr1;
		this.vendorAddrLine2 = addr2;
		this.vendorCity = city;
		this.vendorState = state;
		this.vendorZipCode = zip;
		this.vendorCountry = country;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorAddrLine1() {
		return vendorAddrLine1;
	}
	public void setVendorAddrLine1(String vendorAddrLine1) {
		this.vendorAddrLine1 = vendorAddrLine1;
	}
	public String getVendorAddrLine2() {
		return vendorAddrLine2;
	}
	public void setVendorAddrLine2(String vendorAddrLine2) {
		this.vendorAddrLine2 = vendorAddrLine2;
	}
	public String getVendorCity() {
		return vendorCity;
	}
	public void setVendorCity(String vendorCity) {
		this.vendorCity = vendorCity;
	}
	public String getVendorState() {
		return vendorState;
	}
	public void setVendorState(String vendorState) {
		this.vendorState = vendorState;
	}
	public String getVendorZipCode() {
		return vendorZipCode;
	}
	public void setVendorZipCode(String vendorZipCode) {
		this.vendorZipCode = vendorZipCode;
	}
	public String getVendorCountry() {
		return vendorCountry;
	}
	public void setVendorCountry(String vendorCountry) {
		this.vendorCountry = vendorCountry;
	}

}