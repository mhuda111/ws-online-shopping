package com.project.ws.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Database entity for vendor table.
 * We used JPA annotations to map it with database table.
 */
@XmlRootElement
@Entity
@Table(name="vendor")
public class Vendor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="vendor_id")
	private Integer vendorId;

	@Column(name="vendor_name")
	private String vendorName;

	@Column(name = "vendor_addr_line1")
	private String vendorAddrLine1;
	
	@Column(name = "vendor_addr_line2")
	private String vendorAddrLine2;
	
	@Column(name = "vendor_city")
	private String vendorCity;
	
	@Column(name = "vendor_state")
	private String vendorState;
	
	@Column(name = "vendor_zip_code")
	private String vendorZipCode;
	
	@Column(name = "vendor_country")
	private String vendorCountry;

	@Column(name = "active_flag")
	private String activeFlag;
	
	@Column(name = "amount_paid")
	private Double amount;
	
	
	public Vendor() {
		this.vendorName = "";
		this.vendorAddrLine1 = "";
		this.vendorAddrLine2 = "";
		this.vendorCity = "";
		this.vendorState = "";
		this.vendorZipCode = "";
		this.vendorCountry = "";
		this.activeFlag = "";
		this.amount = 0.00;
	}
	
	public Vendor(String name, String addr1, String addr2, String city, String state, String country, String zip, String flag) {
		
		this.vendorName = name;
		this.vendorAddrLine1 = addr1;
		this.vendorAddrLine2 = addr2;
		this.vendorCity = city;
		this.vendorState = state;
		this.vendorZipCode = zip;
		this.vendorCountry = country;
		this.activeFlag = flag;
		this.amount = 0.00;
	}


	public void setFlag(String flag) {
		this.activeFlag = flag;
	}
	
	public String getFlag() {
		return this.activeFlag;
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
	
	public Double getVendorAmount() {
		return this.amount;
	}
	public void setVendorAmount(Double amount) {
		this.amount = amount;
	}
}