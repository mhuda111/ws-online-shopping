package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Product;

/** 
 * 
 * @author manmeet
 *
 * @description This Repository extends our custom defined functions and default functions of CRUD 
 * 
 */

public interface ProductRepository extends CrudRepository<Product, Integer>, ProductCustomRepository {
	
	public List<Product> findByProductName(String productName);
	@Override
	public List<Product> findAll();

}
