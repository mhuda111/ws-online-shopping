package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	public List<Product> findByProductId(Long productId);
}
