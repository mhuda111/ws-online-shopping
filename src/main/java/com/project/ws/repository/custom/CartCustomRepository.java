package com.project.ws.repository.custom;

import java.util.List;

import com.project.ws.domain.Cart;

public interface CartCustomRepository {
		
		public List<Cart> getCartByCustomerId(Integer id);

	
}
