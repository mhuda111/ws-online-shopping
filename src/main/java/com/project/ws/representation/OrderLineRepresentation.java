package com.project.ws.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "OrderLineItem")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class OrderLineRepresentation {
	
	private Integer orderId;
	
	private Integer productId;
	
	private String productName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	private Integer orderLineQuantity;

	private Double orderLinePrice;

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
