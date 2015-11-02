package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.CustomerAddress;
import com.project.ws.repository.custom.CustomerAddressCustomRepository;

@Repository
public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, Integer>, CustomerAddressCustomRepository{

	    public List<CustomerAddress> findByCustomerId(Integer customerId);
		public List<CustomerAddress> findAll();

}
