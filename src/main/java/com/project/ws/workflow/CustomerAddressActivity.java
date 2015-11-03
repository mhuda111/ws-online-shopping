package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Customer;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.repository.CustomerAddressRepository;
import com.project.ws.representation.AddressRequest;
import com.project.ws.representation.CustAddrRepresentation;

@Component
@Transactional
@Service
public class CustomerAddressActivity {

	private final CustomerAddressRepository addrRepo;
	
	@Autowired
	CustAddrRepresentation custAddrRepresentation;
	
	@Autowired
	CustomerAddress custAddr;
	
	@Autowired
	CustomerAddressActivity(CustomerAddressRepository addrRepo) {
		this.addrRepo = addrRepo;
	}
	
	public List<CustAddrRepresentation> getAddress(Integer customerId) {
		List<CustomerAddress> custAddrList = addrRepo.findByCustomerId(customerId);
		List<CustAddrRepresentation> returnList = new ArrayList<CustAddrRepresentation>();
		for(CustomerAddress a:custAddrList) {
			returnList.add(mapRepresentation(a));
		}
		return returnList;
	}
	
	public CustAddrRepresentation updateCustomerAddress(AddressRequest addr) {
		mapRequest(addr);
		addrRepo.updateCustomerAddress(custAddr);
		custAddr = addrRepo.findOne(addr.getAddrId());
		return mapRepresentation(custAddr);
	}
	
	public void addCustomerAddress(AddressRequest addr) {
		mapRequest(addr);
		addrRepo.addCustomerAddress(custAddr);
//		custAddr = addrRepo.findByCustomerId(customerId);
//		return mapRepresentation(custAddr);
	}
	
	public CustomerAddress mapRequest(AddressRequest addr) {
		custAddr = new CustomerAddress();
		custAddr.setCustAddrLine1(addr.getAddrLine1());
		custAddr.setCustAddrLine2(addr.getAddrLine2());
		custAddr.setCustCity(addr.getCity());
		custAddr.setCustState(addr.getState());
		custAddr.setCustZipCode(addr.getZipCode());
		custAddr.setCustAddrId(addr.getAddrId());
		custAddr.setCustomerId(addr.getCustomerId());
		return custAddr;
	}
	
	public CustAddrRepresentation mapRepresentation(CustomerAddress custAddr) {
		custAddrRepresentation = new CustAddrRepresentation();
		custAddrRepresentation.setCustAddrLine1(custAddr.getCustAddrLine1());
		custAddrRepresentation.setCustAddrLine2(custAddr.getCustAddrLine2());
		custAddrRepresentation.setCustCity(custAddr.getCustCity());
		custAddrRepresentation.setCustState(custAddr.getCustState());
		custAddrRepresentation.setCustZipCode(custAddr.getCustZipCode());
		custAddrRepresentation.setCustomerId(custAddr.getCustomerId());
		return custAddrRepresentation;
	}
}
