package com.project.ws.database.repository.custom;


import com.project.ws.database.domain.CustomerBillingDetails;

public interface CustomerBillingCustomRepository {
	
	public Integer addCardDetails(CustomerBillingDetails customerBillingDetail);
	public Integer chargeCard(Integer customerId, Integer billId, Double amount);
	
}
