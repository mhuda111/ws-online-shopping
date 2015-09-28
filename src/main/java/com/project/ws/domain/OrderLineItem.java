package com.project.ws.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Database entity for order_line_item table.
 * We used JPA annotations to map it with database table.
 */
@Entity
@Table(name="order_line_item")
public class OrderLineItem {

	@Id
	@GeneratedValue
	@Column(name="order_line_id")
	private Integer orderLineId;

	@Column(name="order_id")
	private Integer orderId;

	@Column(name="product_id")
	private Integer productId;

	@Column(name="order_line_quantity")
	private Integer orderLineQuantity;

	@Column(name="order_line_price")
	private Double orderLinePrice;


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


	public Integer getOrderLineQuantity() {
		return orderLineQuantity;
	}


	public void setOrderLineQuantity(Integer orderLineQuantity) {
		this.orderLineQuantity = orderLineQuantity;
	}


	public Double getOrderLinePrice() {
		return orderLinePrice;
	}


	public void setOrderLinePrice(Double orderLinePrice) {
		this.orderLinePrice = orderLinePrice;
	}



}
