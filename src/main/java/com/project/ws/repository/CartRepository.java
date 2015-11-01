package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Cart;
import com.project.ws.repository.custom.CartCustomRepository;

public interface CartRepository extends CrudRepository<Cart, Long>, CartCustomRepository{
	
	    public List<Cart> findByCustomerId(Integer customerId);
	    @Override
		public List<Cart> findAll();


}
