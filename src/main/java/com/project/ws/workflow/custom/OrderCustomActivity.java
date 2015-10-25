package com.project.ws.workflow.custom;

import java.util.List;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Order;
import com.project.ws.representation.OrderRepresentation;

public interface OrderCustomActivity {

	//find methods
	public List<OrderRepresentation> findAllOrders(Integer customerId);
	public List<OrderRepresentation> findAllActiveOrders(Integer customerId);
	public Order findOrder(Integer orderId);
	
	//main methods
	public OrderRepresentation placeOrder(Integer customerId);
	public OrderRepresentation cancelOrder(Integer orderId);
	public Integer updateOrderStatus(Integer orderId, String status);
	public Integer shipOrder(Integer customerId, Integer orderId);
	
	//helper methods
	public List<Cart> readCart(Integer customerId);
	public Integer findActiveOrder(Integer customerId);
	public boolean updateOrderLineItem(Integer customerId);
	
	
}
