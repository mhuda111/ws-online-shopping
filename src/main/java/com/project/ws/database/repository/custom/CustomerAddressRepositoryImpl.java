package com.project.ws.database.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.project.ws.database.domain.CustomerAddress;

public class CustomerAddressRepositoryImpl implements CustomerAddressCustomRepository {
	
		
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
		public List<CustomerAddress> getCustomersAddressByCustId(Integer id) {
			TypedQuery<CustomerAddress> query = em.createQuery("SELECT c FROM CustomerAddress c where cust_id = :idVar", CustomerAddress.class);
			query.setParameter("idVar", id);
			List<CustomerAddress> resultList = query.getResultList();
			return resultList;
		}
		
}
