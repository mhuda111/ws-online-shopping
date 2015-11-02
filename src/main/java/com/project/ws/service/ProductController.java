package com.project.ws.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Product;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.workflow.ProductActivity;


@RestController
public class ProductController implements ErrorController {

	private static final String ERRORPATH = "/error";
	private static final String errorString = "You have received this page in ERROR. ";
	
	private static final Map<Object, String> errorMessages = ImmutableMap.<Object, String>builder()
			.put(HttpServletResponse.SC_NOT_FOUND, "The requested resource does not exist")
			.put(HttpServletResponse.SC_BAD_REQUEST, "The URI entered is incorrect. Please rectify and submit again")
			.put(HttpServletResponse.SC_GATEWAY_TIMEOUT, "Server Error. Please try later")
			.put(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Please contact the System Administrator")
			.put(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "This method is not allowed to access the resource. Please rectify your request")
			.put("Default", "Please contact the System Administrator")
			.build();
	
	@Autowired
    private ProductActivity productActivity;

    @Override
    public String getErrorPath() {
        return ERRORPATH;
    }
    
	@RequestMapping(ERRORPATH)
	public @ResponseBody String error(HttpServletRequest request, HttpServletResponse response) {
		return errorString + errorMessages.get(response.getStatus());
    }
	
	@RequestMapping(value="/product", method=RequestMethod.GET)
    public List<ProductRepresentation> getAllProducts(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = productActivity.allProducts();
    	return productRepresentations;
    }
	
	@RequestMapping(value="/product", method=RequestMethod.GET, params="name")
    public List<ProductRepresentation> readByProductName(HttpServletRequest request) {
		String productName = request.getParameter("name");
    	return productActivity.searchProduct(productName);
    }
	
	@RequestMapping(value="/product/add", method=RequestMethod.POST)
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

	@RequestMapping(value="/product", method=RequestMethod.DELETE, params="productId")
    public String deleteProduct(HttpServletRequest request) {
		Integer productId = Integer.parseInt(request.getParameter("productId"));

		int productDeleted = productActivity.deleteProduct(productId);
		if (productDeleted>0) {
			return "Successful delete product";
		}
		else return "Denied delating product";
    }

}
