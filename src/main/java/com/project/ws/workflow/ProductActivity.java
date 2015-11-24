package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Link;
import com.project.ws.domain.Product;
import com.project.ws.domain.Vendor;
import com.project.ws.repository.CartRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.repository.VendorRepository;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.CartRequest;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.ProductRequest;
import com.project.ws.representation.StringRepresentation;

@Transactional
@Service
@Component
public class ProductActivity {

	private final ProductRepository prodRepo;
	private final CartRepository cartRepo;
	private final VendorRepository vendorRepo;
	private final String baseUrl = "http://localhost:8080";
	
	@Autowired
	Product product;
	
	@Autowired
	ProductRepresentation prodRepresentation;
	
	@Autowired
	CartRepresentation cartRepresentation;
	
	@Autowired
	public ProductActivity(ProductRepository prodRepo, CartRepository cartRepo, VendorRepository vendorRepo) {
		this.prodRepo = prodRepo; 
		this.cartRepo = cartRepo;
		this.vendorRepo = vendorRepo;
	}
	
	public List<CartRepresentation> viewCart(Integer customerId) {
		List<Cart> cartList = new ArrayList<Cart>();
		cartList = cartRepo.findByCustomerId(customerId);
		List<CartRepresentation> resultList = new ArrayList<CartRepresentation>();
		for(Cart c: cartList) {
			System.out.println(c.getCustomerId() + "-" + c.getPrice() + "=" + c.getProductId() + "=" + c.getQuantity());
			resultList.add(mapCartRepresentation(c));
		}
		return resultList;
	}
	
	public StringRepresentation buyProduct(CartRequest cartRequest) {
		Boolean check = false;
		Cart cart = new Cart();
		check = prodRepo.getProductAvailability(cartRequest.getProductId(), cartRequest.getQuantity());
		if(check == false)
			return null;
		cart.setPrice(prodRepo.findOne(cartRequest.getProductId()).getPrice());
		cart.setProductId(cartRequest.getProductId());
		cart.setCustomerId(cartRequest.getCustomerId());
		cart.setQuantity(cartRequest.getQuantity());
		cartRepo.addCart(cart);
		StringRepresentation stringRepresentation = new StringRepresentation();
		stringRepresentation.setMessage("Cart Updated Successfully");
		setLinks(stringRepresentation);
		return stringRepresentation;
	}
	
	public List<ProductRepresentation> allProducts() {
		List<Product> productList = prodRepo.findAll();
		List<ProductRepresentation> resultList = new ArrayList<ProductRepresentation>();
		Vendor vendor;
		String vendorName;
		for(Product p:productList) {
			vendor = vendorRepo.findOne(p.getVendorId());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName));
		}
		return resultList;
	}
	
	public List<ProductRepresentation> searchProduct(String name) {
		List<Product> productList = prodRepo.readByProductName(name);
		if(productList.isEmpty()) return null;
		List<ProductRepresentation> resultList = new ArrayList<ProductRepresentation>();
		Vendor vendor;
		String vendorName;
		for(Product p:productList) {
			System.out.println(p.getProductId() + p.getName() + p.getPrice());
			vendor = vendorRepo.findOne(p.getVendorId());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName));
		}
		return resultList;
	}
	
	public ProductRepresentation addProduct(ProductRequest productRequest) {
		mapRequest(productRequest);
		Integer count = prodRepo.addProduct(product);
		Vendor vendor = new Vendor();
		String vendorName = "";
		if(count == 1) {
			vendor = vendorRepo.findOne(productRequest.getVendorId());
			vendorName = vendor.getVendorName();
		}
		return mapProductRepresentation(product, vendorName);
	}
	
	public Integer deleteProduct(Integer productId) {
		return prodRepo.deleteProduct(productId);
	}
	
	public Boolean validateProduct(Integer productId) {
		Product p = prodRepo.findOne(productId);
		if(p == null)
			return false;
		else
			return true;
	}
	
	public Product mapRequest(ProductRequest request) {
		product = new Product();
		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setVendorId(request.getVendorId());
		product.setType(request.getType());
		product.setDescription(request.getDescription());
		return product;
	}
	
	public ProductRepresentation mapProductRepresentation(Product product, String vendorName) {
		prodRepresentation = new ProductRepresentation();
		prodRepresentation.setProductName(product.getName());
		prodRepresentation.setPrice(product.getPrice());
		prodRepresentation.setQuantity(product.getQuantity());
		prodRepresentation.setVendorName(vendorName);
		prodRepresentation.setProductType(product.getType());
		prodRepresentation.setProductId(product.getProductId());
		setLinks(prodRepresentation);
		return prodRepresentation;
	}
	
	public CartRepresentation mapCartRepresentation(Cart cart) {
		CartRepresentation cartRepresentation = new CartRepresentation();
		cartRepresentation.setProductId(cart.getProductId());
		cartRepresentation.setPrice(cart.getPrice());
		cartRepresentation.setQuantity(cart.getQuantity());
		Product product = prodRepo.findByProductId(cart.getProductId());
		cartRepresentation.setProductName(product.getName());
		setLinks(cartRepresentation);
		return cartRepresentation;
	}
	
	private void setLinks(ProductRepresentation prodRepresentation) {
		Link cart = new Link("post", baseUrl + "/cart/add", "cart");
		prodRepresentation.setLinks(cart);
	}
	
	private void setLinks(CartRepresentation cartRepresentation) {
		Link order = new Link("put", baseUrl + "/order/placeOrder?customerId=", "order");
		cartRepresentation.setLinks(order);
	}
	
	private void setLinks(StringRepresentation stringRepresentation) {
		Link cart = new Link("get", baseUrl + "/cart/view?customerId=", "cart");
		Link order = new Link("put", baseUrl + "/order/placeOrder?customerId=", "order");
		stringRepresentation.setLinks(cart, order);
	}
}
