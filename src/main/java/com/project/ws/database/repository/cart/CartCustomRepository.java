package com.project.ws.database.repository.cart;

import java.util.List;

import com.project.ws.database.domain.Cart;

public interface CartCustomRepository {
		
		public List<Cart> getCartByCustomerId(Integer id);

	
}
