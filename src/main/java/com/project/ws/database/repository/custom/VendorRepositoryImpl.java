package com.project.ws.database.repository.custom;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.project.ws.database.domain.Vendor;

public class VendorRepositoryImpl implements VendorCustomRepository {
	
	
		
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
		public List<Vendor> getVendorByNamesFirstLetter(String letter) {
			TypedQuery<Vendor> query = em.createQuery("SELECT c FROM Vendor c where upper(vendor_name) LIKE :nameVar", Vendor.class);
			query.setParameter("nameVar", letter + "%");
			List<Vendor> resultList = query.getResultList();
			return resultList;
		}

		

	


}
