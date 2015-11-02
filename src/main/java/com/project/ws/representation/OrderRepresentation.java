package com.project.ws.representation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ws.domain.OrderLineItem;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class OrderRepresentation {

	private Integer orderId;
	private Date orderDate;
	private Date orderDeliveryDate;
	private Double orderAmount;
	private String orderShipMethod;
	private String orderStatus;
	private Map<String, Object> products = new HashMap<String, Object>();
	private Integer orderLineQuantity;
	private Double orderLinePrice;

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Map<String, Object> getProducts() {
		return products;
	}

	public void setProducts(OrderLineItem lineItem) {
		products.put("id", lineItem.getProductId());
		products.put("quantity", lineItem.getOrderLineQuantity());
		products.put("price", lineItem.getOrderLinePrice());
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
	
	@Override
	public String toString() {
		return this.orderId + "-" + this.orderShipMethod + "-" + this.orderStatus + "-" + this.orderAmount;
	}
}
