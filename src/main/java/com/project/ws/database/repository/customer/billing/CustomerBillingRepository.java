package com.project.ws.database.repository.customer.billing;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.database.domain.CustomerBillingDetails;

public interface CustomerBillingRepository extends CrudRepository<CustomerBillingDetails, Integer>, CustomerBillingCustomRepository {

	@Override
	public List<CustomerBillingDetails> findAll();
	public List<CustomerBillingDetails> findByCardType(String cardType);
	
}
