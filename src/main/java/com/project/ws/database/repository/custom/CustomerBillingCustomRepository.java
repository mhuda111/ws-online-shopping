package com.project.ws.database.repository.custom;

import java.util.Date;

import com.project.ws.database.domain.CustomerBillingDetails;

public interface CustomerBillingCustomRepository {
	
	public Integer addCardDetails(CustomerBillingDetails customerBillingDetail);
	
}
