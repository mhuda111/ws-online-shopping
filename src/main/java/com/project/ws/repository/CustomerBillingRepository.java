package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.CustomerBillingDetails;

public interface CustomerBillingRepository extends CrudRepository<CustomerBillingDetails, Integer>, CustomerBillingCustomRepository {

	@Override
	public List<CustomerBillingDetails> findAll();
	public List<CustomerBillingDetails> findByCardType(String cardType);
	
}
