package com.project.ws.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.project.ws.domain.Customer;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.workflow.custom.CustomerCustomActivity;

/**
 * This is the implementation class of the customer custom repository interface.
 */
public class CustomerActivityImpl implements CustomerCustomActivity {
	
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
	public List<CustomerRepresentation> getCustomersByNamesFirstLetter(String letter) {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c where upper(cust_firstname) LIKE :nameVar", Customer.class);
		query.setParameter("nameVar", letter + "%");
		List<Customer> resultList = query.getResultList();
		List<CustomerRepresentation> customerList = new ArrayList<CustomerRepresentation>();
		for(Customer c:resultList) {
			customerList.add(this.mapRepresentation(c));
		}
		return customerList;
	}

	@Override
	@Transactional
	public Integer addCustomer(String firstName, String lastName, String email, String password) {
		String SQL = "INSERT INTO customer (cust_firstname, cust_lastname, cust_email, cust_password) VALUES('"+
					firstName + "', '" + lastName + "', '" + email +"', '" + password + "')";
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
				"' where cust_id = " + customerId;
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
		String SQL = "Update customer set cust_email = '" + email + "' where cust_id = " + customerId ;
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
		String SQL = "Update customer set cust_password = '" + password + "' where cust_id = " + customerId ;
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
		String SQL = "Update customer set active_flag = '" + flag + "' where cust_id = " + customerId ;
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
	
	@Override
	public CustomerRepresentation mapRepresentation(Customer customer) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		customerRepresentation.setCustFirstname(customer.getCustFirstname());
		customerRepresentation.setCustLastName(customer.getCustLastName());
		customerRepresentation.setCustEmail(customer.getCustEmail());
		customerRepresentation.setCustId(customer.getCustId());
		return customerRepresentation;
	}

	@Override
	@Transactional
	public Integer deleteCustomer(Integer customerId) {
		String SQL = "DELETE from customer where cust_id = " + customerId ;
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("DELETE successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	

	

}
