package com.project.ws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Customer;
import com.project.ws.repository.custom.CustomerCustomRepository;

/**
 * This is a customer repository interface that extends spring crud repository 
 * and customer custom repository.
 * By using this repository we can do basic Create, Read, Update, Delete 
 * and also do queries with the custom methods that we specified in custom repository.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer>, CustomerCustomRepository {

    public Customer findByCustFirstName(String custFirstname);
    @Override
	public List<Customer> findAll();
    public Customer findByCustEmail(String custEmail);
    public Customer findByCustId(Integer customerId);
}
