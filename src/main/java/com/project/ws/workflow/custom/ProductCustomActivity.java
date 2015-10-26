package com.project.ws.workflow.custom;

import java.util.List;

import com.project.ws.domain.Product;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.ProductRepresentation;

public interface ProductCustomActivity {

	public List<ProductRepresentation> readByProductName(String productName);
	public Boolean getProductAvailability(Integer productId, Integer quantity);
	public String addProduct(Product product);
	public Integer deleteProduct(String productName);
	public Integer updateProductQuantity(Integer productId, Integer quantity, String operation);
	public Integer updateProductPrice(Integer productId, Double price);
	public List<CartRepresentation> buyProduct(Integer customerId, Integer productId, Double price, Integer quantity);
	public ProductRepresentation mapProductRepresentation(Product product);

}
