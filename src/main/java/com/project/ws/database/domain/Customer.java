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
@Table(name="Customer")
public class Customer {
	@Id
	@Column(name="cust_id")
	private Integer custId;
	@Column(name="cust_firstname")
	private String custFirstname;
	@Column(name="cust_lastname")
	private String custLastName;
	@Column(name="cust_email")
	private String custEmail;
	@Column(name="cust_password")
	private String custPassword;
	
	public Customer() {
		this.custFirstname = "";
		this.custLastName = "";
		this.custEmail = "";
		this.custPassword = "";
	}
	
	public Customer(String firstName, String lastName, String email, String password) {
		this.custFirstname = firstName;
		this.custLastName = lastName;
		this.custEmail = email;
		this.custPassword = password;
	}
	
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getCustFirstname() {
		return custFirstname;
	}
	public void setCustFirstname(String custFirstname) {
		this.custFirstname = custFirstname;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getCustPassword() {
		return custPassword;
	}
	public void setCustPassword(String custPassword) {
		this.custPassword = custPassword;
	}
	
	
}
