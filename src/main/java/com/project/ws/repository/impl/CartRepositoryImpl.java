package com.project.ws.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Cart;
import com.project.ws.repository.custom.CartCustomRepository;

public class CartRepositoryImpl implements CartCustomRepository{
	
		
		/**
		 * This EntityManager attribute is used to create the database queries 
		 */
		@PersistenceContext 
		private EntityManager em;
		
		public void setEntityManager(EntityManager em) {
			this.em = em;
		}

		/**
		 * Doing queries from database and mapped the results to the list of customer object.
		 */
		@Override
		public List<Cart> getCartByCustomerId(Integer id) {
			TypedQuery<Cart> query = em.createQuery("SELECT c FROM Cart c where cust_id = " + id, Cart.class);
			List<Cart> resultList = query.getResultList();
			return resultList;
		}
		
		@Override
		@Transactional
		public Integer addCart(Cart cart) {
			Integer count = 0;
			String SQL = "INSERT INTO cart (cust_id, product_id, price, quantity) VALUES (" + 
					cart.getCustomerId() + ", " + cart.getProductId() + ", " + cart.getPrice() + "," + cart.getQuantity() + ")";
			try {
				Query query = em.createNativeQuery(SQL);
				count = query.executeUpdate();
			} catch(Exception e) {
				e.getMessage();
			}
			return count;
		}
		
		@Override
		@Transactional
		public Integer deleteCart(Integer customerId) {
			Integer count = 0;
			String SQL = "delete from cart where cust_id = " + customerId;
			try {
				Query query = em.createNativeQuery(SQL);
				count = query.executeUpdate();
			} catch(Exception e) {
				e.getMessage();
			}
			return count;
		}


}
