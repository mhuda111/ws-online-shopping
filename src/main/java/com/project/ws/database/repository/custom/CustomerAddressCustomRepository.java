package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.CustomerAddress;

public interface CustomerAddressCustomRepository {
		
		public List<CustomerAddress> getCustomersAddressByCustId(Integer id);
		

}
