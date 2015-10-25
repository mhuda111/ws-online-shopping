package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Customer;
import com.project.ws.workflow.custom.CustomerCustomActivity;

/**
 * This is a customer repository interface that extends spring crud repository 
 * and customer custom repository.
 * By using this repository we can do basic Create, Read, Update, Delete 
 * and also do queries with the custom methods that we specified in custom repository.
 */
public interface CustomerActivity extends CrudRepository<Customer, Long>, CustomerCustomActivity {

    public List<Customer> findByCustFirstname(String custFirstname);
    @Override
	public List<Customer> findAll();
    public List<Customer> findByCustEmail(String custEmail);
    public Customer findByCustId(Integer customerId);
}
