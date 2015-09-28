package com.project.ws.database.repository.cart;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.project.ws.database.domain.Cart;

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
			TypedQuery<Cart> query = em.createQuery("SELECT c FROM Cart c where cust_id = :idVar", Cart.class);
			query.setParameter("idVar", id);
			List<Cart> resultList = query.getResultList();
			return resultList;
		}
		


}
