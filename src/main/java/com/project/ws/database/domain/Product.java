package com.project.ws.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	
	@Id
	@Column(name="product_id")
	private Integer productId;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="product_description")
	private String productDescription;
	
	@Column(name="product_type")
	private String productType;

	@Column(name="product_quantity")
	private Integer quantity;
	
	@Column(name="product_price")
	private Double price;
	
	@Column(name="vendor_id")
	private Integer vendorId;
	
	public Product() {
		
	}
	
	public Product(Integer id, String name, String desc, String type) {
		this.productId = id;
		this.productName = name;
		this.productDescription = desc;
		this.productType = type;
	}
	
	//setters
	
	public void setId(Integer id) {
		this.productId = id;
	}
	
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
	
	public Integer getId() {
		return this.productId;
	}
	
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
