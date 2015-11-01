package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

@XmlRootElement(name = "Customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class CustomerRepresentation {

	private Integer custId;
	private String custFirstname;
	private String custLastName;
	private String custEmail;
	private String message;
	
	public CustomerRepresentation() {
		this.custFirstname = "";
		this.custLastName = "";
		this.custEmail = "";
		this.message = "Operation Successful";
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
	
	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
	
	@Override
	public String toString() {
		return this.custId + "-" + this.custFirstname + "-" + this.custLastName + "-" + this.custEmail;
	}
	
}
