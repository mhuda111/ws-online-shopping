package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Product;
import com.project.ws.workflow.custom.ProductCustomRepository;

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
