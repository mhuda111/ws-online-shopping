package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Product;
import com.project.ws.repository.ProductRepository;


@RestController
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

	@RequestMapping("/product")
	public List<Product> getProduct(HttpServletRequest request) {
		String productId = request.getParameter("productId");
		long l = Long.parseLong(productId);
    	return productRepository.findByProductId(l);
	}	
}
