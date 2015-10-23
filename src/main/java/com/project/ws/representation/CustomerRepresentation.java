package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class CustomerRepresentation {

	private String custFirstname;
	private String custLastName;
	private String custEmail;
	
	public CustomerRepresentation() {
		this.custFirstname = "";
		this.custLastName = "";
		this.custEmail = "";
	}
	
	public CustomerRepresentation(String firstName, String lastName, String email, String password) {
		this.custFirstname = firstName;
		this.custLastName = lastName;
		this.custEmail = email;
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
	
}
