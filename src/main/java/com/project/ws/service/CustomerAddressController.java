package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpMethod;
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
import com.project.ws.representation.StringRepresentation;
import com.project.ws.workflow.CustomerActivity;
import com.project.ws.workflow.CustomerAddressActivity;


@RestController
public class CustomerAddressController {

	@Autowired
	CustomerAddressActivity custAddrActivity;
	
	@Autowired
	CustomerActivity customerActivity;
	
	/*
	 * GET to retrieve customer address using customerId
	 */
	@RequestMapping(value="/customeraddress/", method=RequestMethod.GET, params="customerId")
    public List<CustAddrRepresentation> getCustomersAddressFromId(HttpServletRequest request) {
		List<CustAddrRepresentation> custAddrRepresentation = new ArrayList<CustAddrRepresentation>();
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		custAddrRepresentation = custAddrActivity.getAddress(customerId);
    	return custAddrRepresentation;
    }
	
	/*
	 * GET to retrieve customer address using customerId
	 */
	@RequestMapping(value="/customeraddress/", method=RequestMethod.DELETE, params="addrId")
    public StringRepresentation deleteCustomerAddress(HttpServletRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		int addrId = Integer.parseInt(request.getParameter("addrId"));
		stringRepresentation = custAddrActivity.deleteCustomerAddress(addrId);
    	return stringRepresentation;
    }
	
	/*
	 * POST to add a new customer address
	 */
	@RequestMapping(value="/customeraddress/add/", method=RequestMethod.POST)
    public @ResponseBody StringRepresentation addCustomerAddress(@RequestBody AddressRequest request) throws Exception {
		StringRepresentation stringRepresentation = new StringRepresentation();
		int customerId = request.getCustomerId();
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		stringRepresentation = custAddrActivity.addCustomerAddress(request);
		if(stringRepresentation == null)
			throw new Exception("Error adding customer address.");
		return stringRepresentation;
    }
	
	/*
	 * PUT to update customer Address using Address Request
	 */
	@RequestMapping(value="/customeraddress/update/", method=RequestMethod.PUT)
    public StringRepresentation updateCustomerAddress(@RequestBody AddressRequest request) throws Exception {
		StringRepresentation stringRepresentation = new StringRepresentation();
		int customerId = request.getCustomerId();
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		stringRepresentation = custAddrActivity.updateCustomerAddress(request);
		if(stringRepresentation == null)
			throw new Exception("Error updating customer address.");
		return stringRepresentation;
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
