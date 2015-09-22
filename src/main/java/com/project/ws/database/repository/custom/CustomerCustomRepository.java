package com.project.ws.database.repository.custom;

import java.util.List;
import com.project.ws.business.domain.CustomerBO;
import com.project.ws.database.domain.Customer;

/**
 * This is a custom repository that will be used in conjunction with spring crud repository.
 * In this custom repository, we specified custom methods to fetch customer information 
 * based on custom queries.
 */
public interface CustomerCustomRepository {
	
	public List<Customer> getCustomersByNamesFirstLetter(String letter);
	public List<CustomerBO> getCustomerWithAddressById(long id);

}
