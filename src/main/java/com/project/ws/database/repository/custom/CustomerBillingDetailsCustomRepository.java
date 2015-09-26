package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.CustomerBillingDetails;;

public interface CustomerBillingDetailsCustomRepository {
		
		public List<CustomerBillingDetails> getCardNameByBillId(long custBillId);

	}
