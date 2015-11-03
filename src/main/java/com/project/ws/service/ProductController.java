package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Product;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.workflow.ProductActivity;


@RestController
public class ProductController {
	
	@Autowired
    private ProductActivity productActivity;
	
	@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		return "Error: " + message + " in path: " + req.getRequestURI() + ".\n\n Please contact the system administrator ";
    }
	
	@RequestMapping(value="/product", method=RequestMethod.GET)
    public List<ProductRepresentation> getAllProducts(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
		try {
			productRepresentations = productActivity.allProducts();
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
    	return productRepresentations;
    }
	
	@RequestMapping(value="/product", method=RequestMethod.GET, params="name")
    public List<ProductRepresentation> readByProductName(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
		try {
			String productName = request.getParameter("name");
			productRepresentations = productActivity.searchProduct(productName); 
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
    	return productRepresentations;
    }
	
	@RequestMapping(value="/product/add", method=RequestMethod.POST)
    public ProductRepresentation addProduct(HttpServletRequest request) {
		ProductRepresentation productRepresentation = new ProductRepresentation();
		try {
			String productName =  request.getParameter("productName");
			String productDescription =  request.getParameter("productDescription");
			String productType =  request.getParameter("productType");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			Double price = Double.parseDouble(request.getParameter("price"));
			int vendorId = Integer.parseInt(request.getParameter("vendorId"));
			Product product = new Product();
			product.setName(productName);
			product.setDescription(productDescription);
			product.setType(productType);
			product.setQuantity(quantity);
			product.setPrice(price);
			product.setVendorId(vendorId);
			
			productRepresentation = productActivity.addProduct(product);
		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		return productRepresentation;
    }

	@RequestMapping(value="/product", method=RequestMethod.DELETE, params="productId")
    public String deleteProduct(HttpServletRequest request) {
		int productDeleted = 0;
		try {
			Integer productId = Integer.parseInt(request.getParameter("productId"));
			productDeleted = productActivity.deleteProduct(productId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		if (productDeleted>0) {
			return "Successful delete product";
		}
		else return "Denied delating product";
    }

}
