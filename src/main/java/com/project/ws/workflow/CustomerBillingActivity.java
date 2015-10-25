package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.workflow.custom.CustomerBillingCustomActivity;

public interface CustomerBillingActivity extends CrudRepository<CustomerBillingDetails, Integer>, CustomerBillingCustomActivity {

	@Override
	public List<CustomerBillingDetails> findAll();
	public List<CustomerBillingDetails> findByCardType(String cardType);
	
}
