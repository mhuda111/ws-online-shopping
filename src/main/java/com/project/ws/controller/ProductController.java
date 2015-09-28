package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.Product;
import com.project.ws.database.repository.custom.ProductRepository;

/**
 * This is customer spring controller which has methods
 * that specify the end points for the customer web service.
 */
@RestController
public class ProductController {

	@Autowired
    private ProductRepository productRepository;
	@RequestMapping("/product/")
    public List<Product> readByProductName(HttpServletRequest request) {
		String productName = request.getParameter("productName");
    	return productRepository.readByProductName(productName);
    }
	
	@RequestMapping("/product/buy/")
    public String buyProducts(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		Double productPrice = Double.parseDouble(request.getParameter("productPrice"));
		int customerAdded = productRepository.buyProduct(customerId, productId, productPrice);
		if (customerAdded > 0) {
			return "cart inserted successfully. Price of the product is $" + productPrice;
		}
		else return "Failed to add";
    	
    }

}
