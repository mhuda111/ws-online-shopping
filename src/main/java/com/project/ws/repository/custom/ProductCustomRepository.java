package com.project.ws.repository.custom;

import java.util.List;

import com.project.ws.domain.Product;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.ProductRepresentation;

public interface ProductCustomRepository {

	public List<Product> readByProductName(String productName);
	public Boolean getProductAvailability(Integer productId, Integer quantity);
	public String addProduct(Product product);
	public Integer deleteProduct(Integer productId);
	public Integer updateProductQuantity(Integer productId, Integer quantity, String operation);
	public Integer updateProductPrice(Integer productId, Double price);

}
