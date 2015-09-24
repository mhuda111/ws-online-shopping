package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.Cart;
import com.project.ws.database.domain.Order;

public interface OrderCustomRepository {

	//public Integer updateCart(Integer billId);
	public List<Cart> readCart(Integer customerId);
	public Integer placeOrder(Integer customerId, Integer billId);
	
	
}
