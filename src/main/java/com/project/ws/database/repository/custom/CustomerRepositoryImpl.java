package com.project.ws.database.repository.custom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.project.ws.business.domain.CustomerBO;
import com.project.ws.database.domain.Customer;

/**
 * This is the implementation class of the customer custom repository interface.
 */
public class CustomerRepositoryImpl implements CustomerCustomRepository {
	
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
	public List<Customer> getCustomersByNamesFirstLetter(String letter) {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c where upper(cust_firstname) LIKE :nameVar", Customer.class);
		query.setParameter("nameVar", letter + "%");
		List<Customer> resultList = query.getResultList();
		return resultList;
	}

	
	/**
	 * Querying to get the informations from database and
	 * mapping it into business object
	 */
	@Override
	public List<CustomerBO> getCustomerWithAddressById(long id) {
		Query query = em.createNativeQuery("SELECT c.cust_id, c.cust_firstname, c.cust_lastname,"
				+ " ca.cust_addr_code, ca.cust_addr_line1, ca.cust_addr_line2, ca.cust_city,"
				+ " ca.cust_state, ca.cust_zip_code, ca.cust_phone"
				+ " FROM Customer c, Customer_Address ca"
				+ " where c.cust_id = ca.cust_id and c.cust_id = :idVar");
		query.setParameter("idVar", id);
		List<Object[]> resultList = query.getResultList();
		List<CustomerBO> customerBOList = new ArrayList<CustomerBO>();
		for (Object[] objectArray : resultList) {
			CustomerBO customerBO = new CustomerBO();
			customerBO.setCustomerId(getLongFromBigDecimalObject(objectArray[0]));
			customerBO.setFirstName((String) objectArray[1]);
			customerBO.setLastName((String) objectArray[2]);
			customerBO.setAddressType((String) objectArray[3]);
			customerBO.setStreetAddress1((String) objectArray[4]);
			customerBO.setStreetAddress2((String) objectArray[5]);
			customerBO.setCity((String) objectArray[6]);
			customerBO.setState((String) objectArray[7]);
			customerBO.setZipcode((String) objectArray[8]);
			customerBO.setPhone(getLongFromBigDecimalObject(objectArray[9]));
			customerBOList.add(customerBO);
		}
		return customerBOList;
	}
	
	private Long getLongFromBigDecimalObject(Object o) {
		if (o instanceof BigDecimal) {
			BigDecimal num = (BigDecimal) o;
			return num.longValue();
		}
		return null;
	}

}
