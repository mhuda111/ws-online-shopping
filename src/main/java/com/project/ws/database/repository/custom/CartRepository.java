package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.database.domain.Cart;

public interface CartRepository extends CrudRepository<Cart, Long>, CartCustomRepository{
	
	    public List<Cart> findByCustomerId(Integer customerId);
	    @Override
		public List<Cart> findAll();


}
