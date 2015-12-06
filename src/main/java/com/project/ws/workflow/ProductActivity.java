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
	private final VendorRepository vendorRepo;
	private final String baseUrl = "http://localhost:8080";
	private final String mediaType = "application/json";
	
	@Autowired
	Product product;
	
	@Autowired
	ProductRepresentation prodRepresentation;
	
	@Autowired
	CartRepresentation cartRepresentation;
	
	@Autowired
	public ProductActivity(ProductRepository prodRepo, VendorRepository vendorRepo) {
		this.prodRepo = prodRepo; 
		this.vendorRepo = vendorRepo;
	}
	
	public List<ProductRepresentation> allProducts() {
		List<Product> productList = prodRepo.findAll();
		List<ProductRepresentation> resultList = new ArrayList<ProductRepresentation>();
		Vendor vendor;
		String vendorName;
		for(Product p:productList) {
			vendor = vendorRepo.findOne(p.getVendorId());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName, false));
		}
		return resultList;
	}
	
	public List<ProductRepresentation> findAllProductsByVendorId(Integer vendorId) {
		List<Product> productList = prodRepo.findByVendorId(vendorId);
		List<ProductRepresentation> resultList = new ArrayList<ProductRepresentation>();
		Vendor vendor;
		String vendorName;
		for(Product p:productList) {
			vendor = vendorRepo.findOne(p.getVendorId());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName, true));
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
			resultList.add(mapProductRepresentation(p, vendorName, false));
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
		return mapProductRepresentation(product, vendorName, true);
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
	
	public ProductRepresentation mapProductRepresentation(Product product, String vendorName, boolean showLinksForVendor) {
		prodRepresentation = new ProductRepresentation();
		prodRepresentation.setProductName(product.getName());
		prodRepresentation.setPrice(product.getPrice());
		prodRepresentation.setQuantity(product.getQuantity());
		prodRepresentation.setVendorName(vendorName);
		prodRepresentation.setProductType(product.getType());
		prodRepresentation.setProductId(product.getProductId());
		if (showLinksForVendor) {
			setLinksForVendor(prodRepresentation);
		} else {
			setLinksForCustomer(prodRepresentation);
		}
		return prodRepresentation;
	}

	private void setLinksForCustomer(ProductRepresentation prodRepresentation) {
		Link addCart = new Link("post", baseUrl + "/cart/add", "addCart", mediaType);
		Link reviewsToShow = new Link("get", baseUrl + "/review/view?productId=" + prodRepresentation.getProductId(), "showReviews", mediaType);
		Link reviewToAdd = new Link("post", baseUrl + "/review/add", "addReview", mediaType);
		prodRepresentation.setLinks(addCart, reviewsToShow, reviewToAdd);
	}
	
	private void setLinksForVendor(ProductRepresentation prodRepresentation) {
		Link addProduct = new Link("post", baseUrl + "/product/add", "addProduct", mediaType);
		Link deleteProduct = new Link("delete", baseUrl + "/product/delete?productId=" + prodRepresentation.getProductId(), "deleteProduct", mediaType);
		prodRepresentation.setLinks(addProduct, deleteProduct);
	}
}
