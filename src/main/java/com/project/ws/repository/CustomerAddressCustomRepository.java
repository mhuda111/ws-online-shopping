package com.project.ws.repository;

import java.util.List;

import com.project.ws.domain.CustomerAddress;

public interface CustomerAddressCustomRepository {
		
	public List<CustomerAddress> getAddress(Integer customerId);
	public Integer addCustomerAddress(Integer customerId, CustomerAddress customerAddress);
	public Integer updateCustomerAddress(Integer customerId, CustomerAddress customerAddress);
	public Integer setDefaultAddress(Integer addressId);

}
