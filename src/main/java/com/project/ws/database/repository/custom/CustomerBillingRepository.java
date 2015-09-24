package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.database.domain.CustomerBillingDetails;

public interface CustomerBillingRepository extends CrudRepository<CustomerBillingDetails, Integer>, CustomerBillingCustomRepository {

	public List<CustomerBillingDetails> findAll();
	public List<CustomerBillingDetails> findByCardType(String cardType);
	
}
