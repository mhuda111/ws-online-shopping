package com.project.ws.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Database entity for order table.
 * We used JPA annotations to map it with database table.
 */
@XmlRootElement
@Entity
@Table(name="order_details")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="order_id")
	private Integer orderId;
	
	@Column(name="order_date")
	private Date orderDate;
	
	@Column(name="delivery_date")
	private Date orderDeliveryDate;
	
	@Column(name="total_order_amt")
	private Double orderAmount;
	
	@Column(name="tax_amt")
	private Double orderTax;
	
	@Column(name="shipping_charge")
	private Double shipAmount;
	
	@Column(name="shipping_option")
	private String orderShipMethod;
	
	@Column(name="status_cd")
	private String orderStatus;
	
	@Column(name="cust_id")
	private Integer orderCustomerId;
	
	@Column(name="cust_bill_id")
	private Integer billId;

	@Column(name="cust_addr_id")
	private Integer addrId;
	
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
	
	public void setOrderAmount(Double amount) {
		this.orderAmount = amount;
	}
	
	public void setOrderTax(Double tax) {
		this.orderTax = tax;
	}
	
	public void setOrderShipAmount(Double amount) {
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
	
	public Double getOrderAmount() {
		return this.orderAmount;
	}
	
	public Double getOrderTax() {
		return this.orderTax;
	}
	
	public Double getOrderShipAmount() {
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

	public Integer getAddrId() {
		return this.addrId;
	}
	
	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}
	
	@Override
	public String toString() {
		return "Order Details[" + this.orderId + ", " + this.orderStatus + ", " + this.orderDeliveryDate +
				", " + this.orderAmount + ", " + this.orderDate + "]";
	}
}
