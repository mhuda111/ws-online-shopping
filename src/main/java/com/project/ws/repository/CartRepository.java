package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Cart;
import com.project.ws.repository.custom.CartCustomRepository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long>, CartCustomRepository{
	
		public List<Cart> findAll();
		public List<Cart> findByCustomerId(Integer customerId);


}
