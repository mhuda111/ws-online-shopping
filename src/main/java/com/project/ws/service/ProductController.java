package com.project.ws.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Product;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.workflow.ProductActivity;


@RestController
public class ProductController {

	@Autowired
    private ProductActivity productActivity;

	@RequestMapping("/product")
    public List<ProductRepresentation> getAllProducts(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = productActivity.allProducts();
    	return productRepresentations;
    }
	
	@RequestMapping("/product/productName/")
    public List<ProductRepresentation> readByProductName(HttpServletRequest request) {
		String productName = request.getParameter("name");
		System.out.println("name received is  " + productName);
    	return productActivity.searchProduct(productName);
    }
	
	@RequestMapping("/product/add")
    public ProductRepresentation addProduct(HttpServletRequest request) {
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
		return productActivity.addProduct(product);
    }

	@RequestMapping("/product/delete")
    public String deleteProduct(HttpServletRequest request) {
		Integer productId = Integer.parseInt(request.getParameter("productId"));

		int productDeleted = productActivity.deleteProduct(productId);
		if (productDeleted>0) {
			return "Successful delete product";
		}
		else return "Denied delating product";
    }

}
