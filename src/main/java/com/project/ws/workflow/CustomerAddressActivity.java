package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Customer;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.domain.Link;
import com.project.ws.repository.CustomerAddressRepository;
import com.project.ws.representation.AddressRequest;
import com.project.ws.representation.CustAddrRepresentation;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.StringRepresentation;

@Component
@Transactional
@Service
public class CustomerAddressActivity {

	private static final String baseUrl = "http://localhost:8080";
	private static final String mediaType = "application/json";
	
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
		if(custAddrList == null)
			return null;
		List<CustAddrRepresentation> returnList = new ArrayList<CustAddrRepresentation>();
		for(CustomerAddress a:custAddrList) {
			returnList.add(mapRepresentation(a));
		}
		return returnList;
	}
	
	public StringRepresentation updateCustomerAddress(AddressRequest addr) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		mapRequest(addr);
		Integer count = addrRepo.updateCustomerAddress(custAddr);
		if(count == 1)
			stringRepresentation.setMessage("Address updated Successfully");
		else
			return null;
		setLinks(stringRepresentation, addr.getCustomerId());
		return stringRepresentation;
	}
	
	public StringRepresentation deleteCustomerAddress(Integer addrId) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		CustomerAddress customerAddress = addrRepo.findOne(addrId);
		if(customerAddress == null)
			return null;
		if(customerAddress.getDefaultAddr().equals("Y")) {
			stringRepresentation.setMessage("Default address cannot be deleted");
			setLinks(stringRepresentation, customerAddress.getCustomerId());
			return stringRepresentation;
		}
		addrRepo.delete(addrId);
		stringRepresentation.setMessage("Address deleted Successfully");
		setLinks(stringRepresentation, customerAddress.getCustomerId());
		return stringRepresentation;
	}
	
	public StringRepresentation addCustomerAddress(AddressRequest addr) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		mapRequest(addr);
		Integer count = addrRepo.addCustomerAddress(custAddr);
		if(count == 1)
			stringRepresentation.setMessage("Address Added Successfully");
		else
			return null;
		setLinks(stringRepresentation, addr.getCustomerId());
		return stringRepresentation;
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
		custAddrRepresentation.setAddrId(custAddr.getCustAddrId());
		custAddrRepresentation.setCustAddrLine1(custAddr.getCustAddrLine1());
		custAddrRepresentation.setCustAddrLine2(custAddr.getCustAddrLine2());
		custAddrRepresentation.setCustCity(custAddr.getCustCity());
		custAddrRepresentation.setCustState(custAddr.getCustState());
		custAddrRepresentation.setCustZipCode(custAddr.getCustZipCode());
		custAddrRepresentation.setCustomerId(custAddr.getCustomerId());
		setLinks(custAddrRepresentation);
		return custAddrRepresentation;
	}
	
	private void setLinks(CustAddrRepresentation custAddrRepresentation) {
		Link updateAddress = new Link("put", baseUrl + "/customeraddress/update/", "updateAddress", mediaType);
		Link addAddress = new Link("post", baseUrl + "/customeraddress/add/", "addAddress", mediaType);
		Link deleteAddress = new Link("delete", baseUrl + "/customeraddress/?addrId=" + custAddrRepresentation.getAddrId(), "deleteAddress", mediaType);
		custAddrRepresentation.setLinks(deleteAddress, addAddress, updateAddress);
	}
	
	private void setLinks(StringRepresentation stringRepresentation, Integer customerId) {
		Link viewAddress = new Link("get", baseUrl + "/customeraddress/?customerId=" + customerId, "viewAddress", mediaType);
		Link viewCustomer = new Link("get", baseUrl + "/customer/?customerId=" + customerId, "viewCustomer", mediaType);
		stringRepresentation.setLinks(viewAddress, viewCustomer);
	}
}
