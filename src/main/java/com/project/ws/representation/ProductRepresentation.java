package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ProductRepresentation {
	
	private String productName;
	private String productDescription;
	private String productType;
	private Integer quantity;
	private Double price;
	private Integer vendorId;
	
	public ProductRepresentation() {
		this.productDescription = "";
		this.productName = "";
		this.productType = "";
		this.price = 0.00;
		this.quantity = 0;
	}

	public ProductRepresentation(String name, String type, String description, Double price, Integer quantity) {
		this.productDescription = description;
		this.productName = name;
		this.productType = type;
		this.price = price;
		this.quantity = quantity;
	}
	
	public ProductRepresentation(String name, String type, String description, Double price, Integer quantity, Integer vendorId) {
		this.productDescription = description;
		this.productName = name;
		this.productType = type;
		this.price = price;
		this.quantity = quantity;
		this.vendorId = vendorId;
	}
	
	//setters
	public void setName(String name) {
		this.productName = name;
	}
	
	public void setDescription(String desc) {
		this.productDescription = desc;
	}
	
	public void setType(String type) {
		this.productType = type;
	}
	
	public void setVendorId(Integer vendor) {
		this.vendorId = vendor;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	//getters
	
	public String getName() {
		return this.productName;
	}
	
	public String getDescription() {
		return this.productDescription;
	}
	
	public String getType() {
		return this.productType;
	}
	
	public Integer getVendor() {
		return this.vendorId;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public Double getPrice() {
		return this.price;
	}

}
