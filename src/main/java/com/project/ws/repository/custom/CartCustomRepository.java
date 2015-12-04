package com.project.ws.repository.custom;

import java.util.List;

import com.project.ws.domain.Cart;

public interface CartCustomRepository {
		
//		public List<Cart> getCartByCustomerId(Integer customerId);
		public Integer addCart(Cart cart);
		public Integer deleteCart(Integer customerId);
		public Integer updateCart(Integer customerId, Integer billId);
	
}
