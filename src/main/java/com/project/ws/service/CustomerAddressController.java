package com.project.ws.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.CustomerAddress;
import com.project.ws.repository.CustomerAddressRepository;
import com.project.ws.representation.AddressRequest;
import com.project.ws.representation.CustAddrRepresentation;
import com.project.ws.workflow.CustomerAddressActivity;


@RestController
public class CustomerAddressController {

	@Autowired
	CustomerAddressActivity custAddrActivity;
	
	@RequestMapping("/customeraddress/")
    public List<CustAddrRepresentation> getCustomersAddressFromId(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
    	return custAddrActivity.getAddress(customerId);
    }
	
	@RequestMapping(value="/customeraddress/add/", method=RequestMethod.POST)
    public String addCustomerAddress(@RequestBody AddressRequest request) {
		try {
			custAddrActivity.addCustomerAddress(request);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "Error while adding the address";
		}
		return "Address added successfully";
    }
	
	@RequestMapping(value="/customeraddress/update/", method=RequestMethod.PUT)
    public CustAddrRepresentation updateCustomerAddress(@RequestBody AddressRequest request) {
		CustAddrRepresentation custAddrRepresentation = new CustAddrRepresentation();
		try {
			custAddrRepresentation = custAddrActivity.updateCustomerAddress(request);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		if(custAddrRepresentation == null) {
			custAddrRepresentation = new CustAddrRepresentation();
			custAddrRepresentation.setMessage("Operation Failure");
			return custAddrRepresentation;
		}
		else
			return custAddrRepresentation;
    }
	
//	@RequestMapping("/customer/defAddress/")
//	 public String setCustomerDefaultAddress(HttpServletRequest request) {
//		int addressId = Integer.parseInt(request.getParameter("addressId"));
//		int customerUpdate = custAddrActivity.setDefaultAddress(addressId);
//		if (customerUpdate > 0) {
//			return "Successful " ;
//		}
//		else return "Failed to Update";
//	}


}
