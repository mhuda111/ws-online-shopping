package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.Product;

public interface ProductCustomRepository {

	public List<Product> readByProductName(String productName);
	public List<Product> getProductsWithQuantityLessThan(Integer quantity);
	public Integer addProduct(Product product);
	public Integer deleteProduct(String productName);
	public Integer updateProductQuantity(Integer productId, Integer quantity, String operation);
	public Integer updateProductPrice(Integer productId, Double price);
	public Integer buyProduct(Integer customerId, Integer productId, Double productPrice);

}
