package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Product;
import com.project.ws.repository.ProductRepository;

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

	@RequestMapping("/product/add")
    public String addProduct(HttpServletRequest request) {
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

		int productAdded = productRepository.addProduct(product);
		if (productAdded>0) {
			return "Successfully added product";
		}
		else return "Denied adding product";
		//return customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
    }

	@RequestMapping("/product/delete")
    public String deleteProduct(HttpServletRequest request) {
		String productName = request.getParameter("productName");

		int productDeleted = productRepository.deleteProduct(productName);
		if (productDeleted>0) {
			return "Successful delete product";
		}
		else return "Denied delating product";
		//return customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
    }

	@RequestMapping("/product/updateProductQuantity")
    public String updateProductQuantity(HttpServletRequest request) {
		int productId = Integer.parseInt(request.getParameter("productId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String operation = request.getParameter("operation");

		int productQuantityUpdate = productRepository.updateProductQuantity(productId,quantity,operation);
		if (productQuantityUpdate>0) {
			return "Successful updated product" + productId;
		}
		else return "Denied delating product";
		//return customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
    }



}
