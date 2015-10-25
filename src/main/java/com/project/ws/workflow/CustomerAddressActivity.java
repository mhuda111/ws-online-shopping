package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.CustomerAddress;
import com.project.ws.workflow.custom.CustomerAddressCustomActivity;

public interface CustomerAddressActivity extends CrudRepository<CustomerAddress, Long>, CustomerAddressCustomActivity{

	    public List<CustomerAddress> findByCustomerId(Integer customerId);
	    @Override
		public List<CustomerAddress> findAll();
	
}
