package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.Cart;
import com.project.ws.database.domain.Order;

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
