package com.project.ws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Database entity for customer_address table.
 * We used JPA annotations to map it with database table.
 */

@Entity
@Table(name="Customer_Address")
public class CustomerAddress {
	
	@Id
	@GeneratedValue
	@Column(name = "cust_addr_id")
	private Integer custAddrId;
	
//	@ManyToOne
//	@JoinColumn(name="cust_id", referencedColumnName="cust_id")
//    private Customer customer;
	
	@Column(name="cust_id")
	private Integer customerId;
	
	@Column(name = "cust_addr_code")
    private String custAddrCode;
	@Column(name = "cust_addr_line1")
	private String custAddrLine1;
	@Column(name = "cust_addr_line2")
	private String custAddrLine2;
	@Column(name = "cust_city")
	private String custCity;
	@Column(name = "cust_state")
	private String custState;
	@Column(name = "cust_zip_code")
	private String custZipCode;
	@Column(name = "cust_phone")
	private long custPhone;
	
	public Integer getCustAddrId() {
		return custAddrId;
	}
	public void setCustAddrId(Integer custAddrId) {
		this.custAddrId = custAddrId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	
//	public Customer getCustomer() {
//		return customer;
//	}
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
	public String getCustAddrCode() {
		return custAddrCode;
	}
	public void setCustAddrCode(String custAddrCode) {
		this.custAddrCode = custAddrCode;
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
	public long getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(long custPhone) {
		this.custPhone = custPhone;
	}
}

