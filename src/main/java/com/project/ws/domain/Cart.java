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
 * Database entity for cart table.
 * We used JPA annotations to map it with database table.
 */
@XmlRootElement
@Entity
@Table(name="cart")
@Component
public class Cart implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="cust_id")
	private Integer customerId;
	
	@Column(name="product_id")
	private Integer productId;
	
	@Column(name="cust_bill_id")
	private Integer cust_bill_id;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="price")
	private Double price;
	
	
	public Cart() {}
	
	//setters
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public void setCardId(Integer cust_bill_id) {
		this.cust_bill_id = cust_bill_id;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	//getters
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	public Integer getProductId() {
		return this.productId;
	}
	
	public Integer getCardId() {
		return this.cust_bill_id;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public Double getPrice() {
		return this.price;
	}
}
