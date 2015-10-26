package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Product;
import com.project.ws.workflow.custom.ProductCustomActivity;

/** 
 * 
 * @author manmeet
 *
 * @description This Repository extends our custom defined functions and default functions of CRUD 
 * 
 */

public interface ProductActivity extends CrudRepository<Product, Integer>, ProductCustomActivity {
	
	public List<Product> findByProductName(String productName);
	public List<Product> findByProductId(Integer productId);
	public List<Product> findAll();

}
