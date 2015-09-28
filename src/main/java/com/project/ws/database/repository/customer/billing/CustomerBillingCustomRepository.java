package com.project.ws.database.repository.customer.billing;


import com.project.ws.database.domain.CustomerBillingDetails;

public interface CustomerBillingCustomRepository {
	
	public Integer addCardDetails(CustomerBillingDetails customerBillingDetail);
	public Integer updateBillingAddress(Integer customerId, String addrLine1, String addrLine2, String city, String state, String zip);
	public Integer chargeCard(Integer customerId, Integer billId, Double amount);
	
}
