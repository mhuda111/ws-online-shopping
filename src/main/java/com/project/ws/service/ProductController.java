package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Product;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.ProductRequest;
import com.project.ws.workflow.ProductActivity;
import com.project.ws.workflow.VendorActivity;


@RestController
public class ProductController {
	
	@Autowired
    private ProductActivity productActivity;
	
	@Autowired
    private VendorActivity vendorActivity;
	
	@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		return "Error: " + message + " : " + req.getRequestURI() + ".\n\n Or Please contact the system administrator ";
    }
	
	@RequestMapping(value="/product", method=RequestMethod.GET)
    public List<ProductRepresentation> getAllProducts(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
			productRepresentations = productActivity.allProducts();
    	return productRepresentations;
    }
	
	@RequestMapping(value="/product", method=RequestMethod.GET, params="name")
    public List<ProductRepresentation> readByProductName(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
			String productName = request.getParameter("name");
			productRepresentations = productActivity.searchProduct(productName);
			if(productRepresentations == null) {
				throw new ResourceNotFoundException();
			}
    	return productRepresentations;
    }
	
	@RequestMapping(value="/product/add", method=RequestMethod.POST)
    public ProductRepresentation addProduct(@RequestBody ProductRequest productRequest) {
		ProductRepresentation productRepresentation = new ProductRepresentation();
		if(vendorActivity.validateVendor(productRequest.getVendorId()) == false)
			throw new VendorNotFoundException(productRequest.getVendorId());
		productRepresentation = productActivity.addProduct(productRequest);
		return productRepresentation;
    }

	@RequestMapping(value="/product", method=RequestMethod.DELETE, params="productId")
    public String deleteProduct(HttpServletRequest request) {
		int productDeleted = 0;
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		if(productActivity.validateProduct(productId) == false) 
			throw new ProductNotFoundException(productId);
		productDeleted = productActivity.deleteProduct(productId);
		if (productDeleted>0) {
			return "Successful delete product";
		}
		else return "Denied delating product";
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(Integer productId) {
		super("Could not find product - " + productId);
	}
}
