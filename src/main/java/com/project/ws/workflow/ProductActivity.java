package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Product;
import com.project.ws.domain.Vendor;
import com.project.ws.repository.CartRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.repository.VendorRepository;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.CartRequest;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.ProductRequest;

@Transactional
@Service
@Component
public class ProductActivity {

	private final ProductRepository prodRepo;
	private final CartRepository cartRepo;
	private final VendorRepository vendorRepo;
	
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
	
	public List<CartRepresentation> buyProduct(CartRequest cartRequest) {
		System.out.println("in buy");
		Boolean check = false;
		Cart cart = new Cart();
		System.out.println(cartRequest.getCustomerId());
		check = prodRepo.getProductAvailability(cartRequest.getProductId(), cartRequest.getQuantity());
		System.out.println("after prod availability");
		if(check == false)
			return null;
		cart.setPrice(prodRepo.findOne(cartRequest.getProductId()).getPrice());
		cart.setProductId(cartRequest.getProductId());
		cart.setCustomerId(cartRequest.getCustomerId());
		cart.setQuantity(cartRequest.getQuantity());
		cartRepo.addCart(cart);
		List<Cart> cartList = new ArrayList<Cart>();
		cartList = cartRepo.getCartByCustomerId(cartRequest.getCustomerId());
		List<CartRepresentation> resultList = new ArrayList<CartRepresentation>();
		for(Cart c: cartList) {
			resultList.add(mapCartRepresentation(c));
		}
		return resultList;
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
		prodRepresentation.setName(product.getName());
		prodRepresentation.setPrice(product.getPrice());
		prodRepresentation.setQuantity(product.getQuantity());
		prodRepresentation.setVendorName(vendorName);
		prodRepresentation.setType(product.getType());
		prodRepresentation.setProductId(product.getProductId());
		return prodRepresentation;
	}
	
	public CartRepresentation mapCartRepresentation(Cart cart) {
		cartRepresentation.setProductId(cart.getProductId());
		cartRepresentation.setPrice(cart.getPrice());
		cartRepresentation.setQuantity(cart.getQuantity());
		return cartRepresentation;
	}
	
}
