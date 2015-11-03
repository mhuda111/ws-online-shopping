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
import com.project.ws.representation.ProductRepresentation;

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
	
	public List<CartRepresentation> buyProduct(Cart cart) {
		Boolean check = false;
		check = prodRepo.getProductAvailability(cart.getProductId(), cart.getQuantity());
		if(check == false)
			return null;
		cartRepo.addCart(cart);
		List<Cart> cartList = new ArrayList<Cart>();
		cartList = cartRepo.getCartByCustomerId(cart.getCustomerId());
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
			vendor = vendorRepo.findOne(p.getVendor());
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
			vendor = vendorRepo.findOne(p.getVendor());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName));
		}
		return resultList;
	}
	
	public ProductRepresentation addProduct(Product product) {
		prodRepo.addProduct(product);
		Vendor vendor = vendorRepo.findOne(product.getVendor());
		return mapProductRepresentation(product, vendor.getVendorName());
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
	
	public ProductRepresentation mapProductRepresentation(Product product, String vendorName) {
		prodRepresentation = new ProductRepresentation();
		prodRepresentation.setName(product.getName());
		prodRepresentation.setPrice(product.getPrice());
		prodRepresentation.setQuantity(product.getQuantity());
		prodRepresentation.setVendorName(vendorName);
		prodRepresentation.setType(product.getType());
		return prodRepresentation;
	}
	
	public CartRepresentation mapCartRepresentation(Cart cart) {
		cartRepresentation.setProductId(cart.getProductId());
		cartRepresentation.setPrice(cart.getPrice());
		cartRepresentation.setQuantity(cart.getQuantity());
		return cartRepresentation;
	}
	
}
