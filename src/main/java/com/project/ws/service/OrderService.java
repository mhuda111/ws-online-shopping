package com.project.ws.service;

import java.util.List;

import com.project.ws.domain.Order;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.OrderRequest;


public interface OrderService {
	
    public List<OrderRepresentation> findAllOrders(Integer customerId);
    public List<OrderRepresentation> findAllActiveOrders(Integer customerId);
	public List<CartRepresentation> createOrder(OrderRequest orderRequest);
    public OrderRepresentation placeOrder(OrderRequest orderRequest);
	public OrderRepresentation cancelOrder(Integer orderId);
	public Order checkOrderStatus(Integer orderId);
}
