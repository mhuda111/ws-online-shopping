package com.project.ws.repository;

import java.util.List;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Order;

public interface OrderCustomRepository {

	public List<Order> findAllOrders(Integer customerId);
	public List<Order> findAllActiveOrders(Integer customerId);
	public List<Cart> readCart(Integer customerId);
	public Integer placeOrder(Integer customerId, Integer billId);
	public Integer cancelOrder(Integer orderId);
	public Integer findActiveOrder(Integer customerId);
	public List<Order> getOrderStatus(Integer customerId, Integer orderId);
	public Integer updateOrderStatus(Integer orderId, String status);
	public Integer shipOrder(Integer customerId, Integer orderId);
}
