package com.project.ws.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Database entity for order table.
 * We used JPA annotations to map it with database table.
 */
@Entity
@Table(name="order_details")
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name="order_id")
	private Integer orderId;
	
	@Column(name="order_date")
	private Date orderDate;
	
	@Column(name="delivery_date")
	private Date orderDeliveryDate;
	
	@Column(name="total_order_amt")
	private Float orderAmount;
	
	@Column(name="tax_amt")
	private Float orderTax;
	
	@Column(name="shipping_charge")
	private Float shipAmount;
	
	@Column(name="shipping_option")
	private String orderShipMethod;
	
	@Column(name="status_cd")
	private String orderStatus;
	
	@Column(name="cust_id")
	private Integer orderCustomerId;
	
	@Column(name="cust_bill_id")
	private Integer billId;

	
	public Order() {
		
	}
	
	//setters
	
//	public void setOrderId(Integer id) {
//		this.orderId = id;
//	}

	public void setOrderDate(Date date) {
		this.orderDate = date;
	}
	
	public void setOrderDeliveryDate(Date date) {
		this.orderDeliveryDate = date;
	}
	
	public void setOrderShipMethod(String shipMethod) {
		this.orderShipMethod = shipMethod;
	}
	
	public void setOrderAmount(Float amount) {
		this.orderAmount = amount;
	}
	
	public void setOrderTax(Float tax) {
		this.orderTax = tax;
	}
	
	public void setOrderShipAmount(Float amount) {
		this.shipAmount = amount;
	}
	
	public void setCustomerId(Integer id) {
		this.orderCustomerId = id;
	}
	
//	public void setOrderAddrCode(String code) {
//		this.orderAddrCode = code;
//	}
	
	public void setOrderStatus(String status) {
		this.orderStatus =status;
	}
	
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	
	//getters
	
	public Integer getOrderId() {
		return this.orderId;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}
	
	public Date getOrderDeliveryDate() {
		return this.orderDeliveryDate;
	}
	
	public String getOrderShipMethod() {
		return this.orderShipMethod;
	}
	
	public Float getOrderAmount() {
		return this.orderAmount;
	}
	
	public Float getOrderTax() {
		return this.orderTax;
	}
	
	public Float getOrderShipAmount() {
		return this.shipAmount;
	}
	
	public Integer getCustomerId() {
		return this.orderCustomerId;
	}
	
//	public String getOrderAddrCode() {
//		return this.orderAddrCode;
//	}
	
	public String getOrderStatus() {
		return this.orderStatus;
	}
	
	public Integer getBillId() {
		return this.billId;
	}
	
	@Override
	public String toString() {
		return "Order Details[" + this.orderId + ", " + this.orderStatus + ", " + this.orderDeliveryDate +
				", " + this.orderAmount + ", " + this.orderDate + "]";
	}
}
