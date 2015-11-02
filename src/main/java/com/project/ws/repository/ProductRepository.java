package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Product;
import com.project.ws.repository.custom.ProductCustomRepository;

/** 
 * 
 * @author manmeet
 *
 * @description This Repository extends our custom defined functions and default functions of CRUD 
 * 
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, ProductCustomRepository {
	
	public List<Product> findByProductName(String productName);
	public Product findByProductId(Integer productId);
	public List<Product> findAll();

}
