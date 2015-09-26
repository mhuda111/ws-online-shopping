package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.project.ws.database.domain.CustomerBillingDetails;

public interface CustomerBillingDetailsRepository extends CrudRepository<CustomerBillingDetails, Long>, CustomerBillingDetailsCustomRepository {
	
	public List<CustomerBillingDetails> findByCardName(String cardName);
    public List<CustomerBillingDetails> findAll();

}