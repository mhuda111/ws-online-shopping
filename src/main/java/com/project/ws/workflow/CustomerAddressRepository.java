package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.CustomerAddress;
import com.project.ws.workflow.custom.CustomerAddressCustomRepository;

public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, Long>, CustomerAddressCustomRepository{

	    public List<CustomerAddress> findByCustomerId(Integer customerId);
	    @Override
		public List<CustomerAddress> findAll();
	
}
