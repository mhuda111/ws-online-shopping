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
	private String productType;
	private Integer quantity;
	private Double price;

	public ProductRepresentation() {
		this.productName = "";
		this.productType = "";
		this.price = 0.00;
		this.quantity = 0;
	}

	public ProductRepresentation(String name, String type, String description, Double price, Integer quantity) {
		this.productName = name;
		this.productType = type;
		this.price = price;
		this.quantity = quantity;
	}
	
	//setters
	public void setName(String name) {
		this.productName = name;
	}
	
	public void setType(String type) {
		this.productType = type;
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
	
	public String getType() {
		return this.productType;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public Double getPrice() {
		return this.price;
	}

}
