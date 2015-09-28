package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.CustomerAddress;

public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, Long>, CustomerAddressCustomRepository{

	    public List<CustomerAddress> findByCustomerId(Integer customerId);
	    @Override
		public List<CustomerAddress> findAll();
	
}
