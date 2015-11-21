package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@Component
public class ProductRepresentation extends AbstractRepresentation {
	
	private Integer productId;
	private String productName;
	private String productType;
	private Integer quantity;
	private Double price;
	private String vendorName;

	public ProductRepresentation() {
	}
	
	//setters
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public void setProductType(String type) {
		this.productType = type;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
		
	//getters
	public String getProductName() {
		return this.productName;
	}
	
	public String getVendorName() {
		return this.vendorName;
	}
	
	public String getProductType() {
		return this.productType;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public Integer getProductId() {
		return this.productId;
	}
	
	public Double getPrice() {
		return this.price;
	}

}
