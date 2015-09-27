package com.project.ws.database.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is sample business object that contains customer information with address
 * which we may expose to others.
 *
 */
@Entity
@Table(name="order_line_item")
public class OrderLineItem {

	@Id
	@Column(name="order_line_id")
	private Integer orderLineId;

	@Column(name="order_id")
	private Integer orderId;

	@Column(name="product_id")
	private Integer productId;

	@Column(name="order_line_quantity")
	private float orderLineQuantity;

	@Column(name="order_line_price")
	private float orderLinePrice;


	public OrderLineItem() {

	}


	public Integer getOrderLineId() {
		return orderLineId;
	}


	public void setOrderLineId(Integer orderLineId) {
		this.orderLineId = orderLineId;
	}


	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public float getOrderLineQuantity() {
		return orderLineQuantity;
	}


	public void setOrderLineQuantity(float orderLineQuantity) {
		this.orderLineQuantity = orderLineQuantity;
	}


	public float getOrderLinePrice() {
		return orderLinePrice;
	}


	public void setOrderLinePrice(float orderLinePrice) {
		this.orderLinePrice = orderLinePrice;
	}



}
