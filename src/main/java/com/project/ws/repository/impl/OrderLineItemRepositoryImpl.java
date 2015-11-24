package com.project.ws.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.ws.domain.Cart;
import com.project.ws.repository.CartRepository;
import com.project.ws.repository.ProductRepository;


public class OrderLineItemRepositoryImpl {
	
	@PersistenceContext 
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	private Double orderAmount = 0.00;
	private Integer count = 0;
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	ProductRepository prodRepo;
	
	public void addOrderLine(Integer customerId, Integer orderId) {

		List<Cart> list = cartRepo.findByCustomerId(customerId);
		String SQL = "";
		
		for(Cart cart:list) {
			prodRepo.updateProductQuantity(cart.getProductId(), cart.getQuantity(), "subtract");
			System.out.println("adding in order_line_item");
			SQL = "insert into order_line_item (order_id, product_id, order_line_quantity, order_line_price) " +
					"values(" + orderId + ", " + cart.getProductId() + ", " + cart.getQuantity() + ", " +
					cart.getPrice() + ")";
			System.out.println(SQL);
			em.createNativeQuery(SQL).executeUpdate();
		}
		
		SQL = "delete from cart where cust_id = " + customerId;
		em.createNativeQuery(SQL).executeUpdate();
	}
}
