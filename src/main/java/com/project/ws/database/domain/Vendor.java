package com.project.ws.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Database entity for customer table.
 * We used JPA annotations to map it with database table.
 */
@Entity
@Table(name="VENDOR")
public class Vendor {
	@Id
	@Column(name="vendor_id")
	private long vendorId;

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
	@Column(name = "vendor_state")
	private String vendorCountry;

	public Vendor(){

	};


	public long getVendorId() {
		return vendorId;
	}
	public void setVendorId(long vendorId) {
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