package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

@XmlRootElement(name = "Cart")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Component
public class CartRepresentation {

	private Integer productId;
	
	private Integer quantity;
	
	private Double price;
	
	public CartRepresentation() {
		
	}
	
	//setters
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	//getters
		
	public Integer getProductId() {
		return this.productId;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public Double getPrice() {
		return this.price;
	}
}
