package com.project.ws.representation;

import org.springframework.stereotype.Component;

@Component
public class ProductRequest {

	private String productName;
	private String productDescription;
	private String productType;
	private Integer quantity;
	private Double price;
	private Integer vendorId;
	
	public ProductRequest() {
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
	
	public Integer getVendorId() {
		return this.vendorId;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	@Override
	public String toString() {
		return "[" + this.productName + "-" + this.productDescription + "-" + this.productType + "-" +
				this.quantity + "-" + this.price + "]";
	}

}
