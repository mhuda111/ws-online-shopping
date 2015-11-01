package com.project.ws.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

/**
 * Database entity for customer table.
 * We used JPA annotations to map it with database table.
 */

@Component
@Entity
@Table(name="Customer")
public class Customer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="cust_id")
	private Integer custId;
	@Column(name="cust_firstname")
	private String custFirstName;
	@Column(name="cust_lastname")
	private String custLastName;
	@Column(name="cust_email")
	private String custEmail;
	@Column(name="cust_password")
	private String custPassword;
	
	public Customer() {
		this.custFirstName = "";
		this.custLastName = "";
		this.custEmail = "";
		this.custPassword = "";
	}
	
	public Customer(String firstName, String lastName, String email, String password) {
		this.custFirstName = firstName;
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
		return custFirstName;
	}
	public void setCustFirstname(String custFirstname) {
		this.custFirstName = custFirstname;
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
	
	@Override
	public String toString() {
		return this.custFirstName + "-" + this.custLastName + "-" + this.custEmail + "-" + this.custPassword;
	}
	
}
