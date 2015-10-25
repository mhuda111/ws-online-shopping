package com.project.ws.workflow.custom;

import java.util.List;

import com.project.ws.domain.CustomerAddress;

public interface CustomerAddressCustomActivity {
		
	public List<CustomerAddress> getAddress(Integer customerId);
	public Integer addCustomerAddress(Integer customerId, CustomerAddress customerAddress);
	public Integer updateCustomerAddress(Integer customerId, CustomerAddress customerAddress);
	public Integer setDefaultAddress(Integer addressId);

}
