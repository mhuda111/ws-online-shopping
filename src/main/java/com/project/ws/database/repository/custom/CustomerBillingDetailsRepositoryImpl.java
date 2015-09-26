package com.project.ws.database.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.project.ws.database.domain.CustomerBillingDetails;



public class CustomerBillingDetailsRepositoryImpl implements CustomerBillingDetailsCustomRepository{
	
	@PersistenceContext 
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Doing queries from database and mapped the results to the list of customer object.
	 */
	@Override
	public List<CustomerBillingDetails> getCardNameByBillId(long custBillId) {
		TypedQuery<CustomerBillingDetails> query = em.createQuery("SELECT c FROM CustomerBillingDetails c where cust_bill_id = :idVar", CustomerBillingDetails.class);
		query.setParameter("idVar", custBillId);
		List<CustomerBillingDetails> resultList = query.getResultList();
		return resultList;
	}

}
