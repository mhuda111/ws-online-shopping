package com.project.ws.repository.custom;

import java.util.List;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Order;
import com.project.ws.representation.OrderRepresentation;

public interface OrderCustomRepository {

	//find methods
	public List<Order> findAllOrders(Integer customerId);
	public List<Order> findAllActiveOrders(Integer customerId);
	public Order findOrder(Integer orderId);
	
	public Integer addOrder(Order order);
	public Integer findLatestOrder(Integer customerId);
	public Integer updateOrderStatus(Integer orderId, String status);
	public List<Order> findAllOrdersForVendor(Integer vendorId, String orderStatus);
}
