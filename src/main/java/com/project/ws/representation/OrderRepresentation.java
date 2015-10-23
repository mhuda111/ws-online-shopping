package com.project.ws.representation;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class OrderRepresentation {

	private Integer orderId;
	private Date orderDate;
	private Date orderDeliveryDate;
	private Double orderAmount;
	private String orderShipMethod;
	private String orderStatus;
	private Integer productId;
	private Integer orderLineQuantity;
	private Double orderLinePrice;

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
	
	public OrderRepresentation() {
		
	}
	
	//setters
	
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
		
	public void setOrderStatus(String status) {
		this.orderStatus =status;
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
	
	public String getOrderStatus() {
		return this.orderStatus;
	}

}
