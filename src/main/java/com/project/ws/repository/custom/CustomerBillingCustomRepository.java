package com.project.ws.repository.custom;


import com.project.ws.domain.CustomerBillingDetails;

public interface CustomerBillingCustomRepository {
	
	public Integer addCardDetails(CustomerBillingDetails customerBillingDetail);
	public Integer updateBillingAddress(Integer customerId, String addrLine1, String addrLine2, String city, String state, String zip);
	public Integer getBillId(Integer customerId, String cardType);
	public Integer updateAmount(Integer customerId, Integer billId, Double amount, String type);

}
