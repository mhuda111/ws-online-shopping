package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.workflow.custom.CustomerBillingCustomRepository;

public interface CustomerBillingRepository extends CrudRepository<CustomerBillingDetails, Integer>, CustomerBillingCustomRepository {

	@Override
	public List<CustomerBillingDetails> findAll();
	public List<CustomerBillingDetails> findByCardType(String cardType);
	
}
