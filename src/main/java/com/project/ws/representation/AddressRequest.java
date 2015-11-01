package com.project.ws.representation;

import org.springframework.stereotype.Component;

@Component
public class AddressRequest {

	private Integer customerId;
	private Integer addrId;
	private String addrLine1;
	private String addrLine2;
	private String city;
	private String state;
	private String zipCode;
	
	public AddressRequest() {}
	
	public String getAddrLine1() {
		return addrLine1;
	}
	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}
	public String getAddrLine2() {
		return addrLine2;
	}
	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Integer getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getAddrId() {
		return this.addrId;
	}
	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}
	
}
