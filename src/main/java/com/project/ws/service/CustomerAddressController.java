package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.repository.CustomerAddressRepository;
import com.project.ws.representation.AddressRequest;
import com.project.ws.representation.CustAddrRepresentation;
import com.project.ws.workflow.CustomerActivity;
import com.project.ws.workflow.CustomerAddressActivity;


@RestController
public class CustomerAddressController {

	@Autowired
	CustomerAddressActivity custAddrActivity;
	
	@Autowired
	CustomerActivity customerActivity;
	
	@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		return "Error: " + message + " in path: " + req.getRequestURI() + ".\n\n Please contact the system administrator ";
    }
	
	@RequestMapping("/customeraddress/")
    public List<CustAddrRepresentation> getCustomersAddressFromId(HttpServletRequest request) {
		List<CustAddrRepresentation> custAddrRepresentation = new ArrayList<CustAddrRepresentation>();
		try {
			int customerId = Integer.parseInt(request.getParameter("customerId"));
			if(customerActivity.validateCustomer(customerId) == false)
				throw new CustomerNotFoundException(customerId);
			custAddrRepresentation = custAddrActivity.getAddress(customerId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
    	return custAddrRepresentation;
    }
	
	@RequestMapping(value="/customeraddress/add/", method=RequestMethod.POST)
    public String addCustomerAddress(@RequestBody AddressRequest request) {
		try {
			int customerId = request.getCustomerId();
			if(customerActivity.validateCustomer(customerId) == false)
				throw new CustomerNotFoundException(customerId);
			custAddrActivity.addCustomerAddress(request);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return "Address added successfully";
    }
	
	@RequestMapping(value="/customeraddress/update/", method=RequestMethod.PUT)
    public CustAddrRepresentation updateCustomerAddress(@RequestBody AddressRequest request) {
		CustAddrRepresentation custAddrRepresentation = new CustAddrRepresentation();
		try {
			int customerId = request.getCustomerId();
			if(customerActivity.validateCustomer(customerId) == false)
				throw new CustomerNotFoundException(customerId);
			custAddrRepresentation = custAddrActivity.updateCustomerAddress(request);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
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
