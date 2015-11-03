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
 * Database entity for product table.
 * We used JPA annotations to map it with database table.
 */
@XmlRootElement
@Entity
@Table(name="product")
@Component
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
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
		this.productDescription = "";
		this.productName = "";
		this.productType = "";
		this.price = 0.00;
		this.quantity = 0;
	}

	public Product(String name, String type, String description, Double price, Integer quantity) {
		this.productDescription = description;
		this.productName = name;
		this.productType = type;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Product(String name, String type, String description, Double price, Integer quantity, Integer vendorId) {
		this.productDescription = description;
		this.productName = name;
		this.productType = type;
		this.price = price;
		this.quantity = quantity;
		this.vendorId = vendorId;
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
