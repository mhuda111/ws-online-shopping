package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.Product;

public interface ProductCustomRepository {
	
	public List<Product> readByProductName(String productName);
	public List<Product> getProductsWithQuantityLessThan(Integer quantity);
	public Integer deleteProduct(String productName);
	public Integer updateProductName(String oldProductName, String newProductName);

}
