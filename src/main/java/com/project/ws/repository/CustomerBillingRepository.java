package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.repository.custom.CustomerBillingCustomRepository;

@Repository
public interface CustomerBillingRepository extends CrudRepository<CustomerBillingDetails, Integer>, CustomerBillingCustomRepository {

	public List<CustomerBillingDetails> findAll();
	public CustomerBillingDetails findOne();
	
	public List<CustomerBillingDetails> findByCustomerId(Integer customerId);
	public List<CustomerBillingDetails> findByCardType(Integer customerId);
}
