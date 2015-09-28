package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.database.domain.Product;

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
