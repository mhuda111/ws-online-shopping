package com.project.ws.database.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

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

	@Override
	@Transactional
	public Integer addCustomer(String firstName, String lastName, String email) {
		String SQL = "INSERT INTO customer (cust_first_name, cust_last_name, cust_email) VALUES(" +
					firstName + ", " + lastName + ", " + email + ")";
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("customer added successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer updateName(Integer customerId, String firstName, String lastName) {
		String SQL = "Update customer set cust_firstname = '" + firstName + "', cust_lastname = '" + lastName +
				"' where cust_id = " + customerId + ")";
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("name updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer updateEmail(Integer customerId, String email) {
		String SQL = "Update customer set cust_email = '" + email + "' where cust_id = " + customerId + ")";
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("email updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer updatePassword(Integer customerId, String password) {
		String SQL = "Update customer set cust_password = '" + password + "' where cust_id = " + customerId + ")";
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("password updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer changeStatus(Integer customerId, char flag) {
		String SQL = "Update customer set active_flag = '" + flag + "' where cust_id = " + customerId + ")";
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("status updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	public void notifyCustomer(Integer customerId) {

	}

}
