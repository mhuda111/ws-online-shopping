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
import com.project.ws.repository.CartRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.CartRequest;
import com.project.ws.representation.StringRepresentation;

@Component
@Transactional
@Service
public class CartActivity {
	
	private final ProductRepository prodRepo;
	private final CartRepository cartRepo;
	
	@Autowired
	CartRepresentation cartRepresentation;
	
	@Autowired
	CartActivity(CartRepository cartRepo, ProductRepository prodRepo) {
		this.cartRepo = cartRepo;
		this.prodRepo = prodRepo;
	}
	
	private final String baseUrl = "http://localhost:8080";
	private final String mediaType = "application/json";
	
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
		setLinks(stringRepresentation, cart.getCustomerId());
		return stringRepresentation;
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
	
	public StringRepresentation updateCart(Integer customerId, Integer billId) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		List<Cart> cart = cartRepo.findByCustomerId(customerId);
		if(cart.size() == 0) { 
			stringRepresentation.setMessage("No contents in cart to apply the payment to.");
			setLinks(stringRepresentation, customerId);
			return stringRepresentation;
		}
		Integer count = cartRepo.updateCart(customerId, billId);
		if(count == 0)
			stringRepresentation.setMessage("Error selecting payment. please try again."); 
		else
			stringRepresentation.setMessage("Payment selected successfully. Please proceed to place order.");
		setLinks(stringRepresentation, customerId);
		return stringRepresentation;
	}
	
	public CartRepresentation mapCartRepresentation(Cart cart) {
		CartRepresentation cartRepresentation = new CartRepresentation();
		cartRepresentation.setProductId(cart.getProductId());
		cartRepresentation.setPrice(cart.getPrice());
		cartRepresentation.setQuantity(cart.getQuantity());
		Product product = prodRepo.findByProductId(cart.getProductId());
		cartRepresentation.setProductName(product.getName());
		setLinks(cartRepresentation, cart.getCustomerId());
		return cartRepresentation;
	}
	
	private void setLinks(StringRepresentation stringRepresentation, Integer customerId) {
		Link placeOrder = new Link("put", baseUrl + "/order/placeOrder?customerId=" + customerId, "placeOrder", mediaType);
		Link selectPayment = new Link("get", baseUrl + "/billing/?customerId=" + customerId, "selectPayment", mediaType);
		Link viewCart = new Link("get", baseUrl + "/cart/view?customerId=", "viewCart", mediaType);
		stringRepresentation.setLinks(viewCart, placeOrder, selectPayment);
	}
	
	private void setLinks(CartRepresentation cartRepresentation, Integer customerId) {
		Link checkOut = new Link("get", baseUrl + "/billing/?customerId=" + customerId, "checkOut", mediaType);
		Link showAll = new Link("get", baseUrl + "/products", "showAll", mediaType);
		Link selectPayment = new Link("get", baseUrl + "/billing/?customerId=" + customerId, "selectPayment", mediaType);
		Link placeOrder = new Link("put", baseUrl + "/order/placeOrder?customerId=" + customerId, "placeOrder", mediaType);
		cartRepresentation.setLinks(checkOut, showAll, placeOrder, selectPayment);
	}
}
