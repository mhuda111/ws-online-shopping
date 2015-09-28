package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.CustomerAddress;
import com.project.ws.database.repository.customer.address.CustomerAddressRepository;


@RestController
public class CustomerAddressController {

	@Autowired
    private CustomerAddressRepository customerAddressRepository;

	/*
	 * This expose "/customer/" end point and looks for a URL parameter "id"
	 * then gets customer information with address.  
	 */
//	@RequestMapping("/customer")
//    public List<CustomerBO> getCustomers(HttpServletRequest request) {
//		String id = request.getParameter("id");
//    	return customerRepository.getCustomerWithAddressById(Long.parseLong(id));
//    }
	
	/*
	 * This expose "/customer/firstLetter/" end point and looks for a URL parameter "letter"
	 * then gets customer information based on first name's first letter
	 * of the customer
	 */
	
	@RequestMapping("/customeraddress/id/")
    public List<CustomerAddress> getCustomersAddressFromId(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
    	return customerAddressRepository.getAddress(customerId);
    }
	
	
	@RequestMapping("/customer/addCustomerAddress/")
    public String addCustomerAddress(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String custAddrLine1 = request.getParameter("custAddrLine1");
		String custAddrLine2 = request.getParameter("custAddrLine2");
		String custCity = request.getParameter("custCity");
		String custState = request.getParameter("custState");
		String custZipCode = request.getParameter("custZipCode");
		Long custPhone = Long.parseLong(request.getParameter("custPhone"));
		
		CustomerAddress customerAddress = new CustomerAddress();
		customerAddress.setCustAddrLine1(custAddrLine1);
		customerAddress.setCustAddrLine2(custAddrLine2);
		customerAddress.setCustCity(custCity);
		customerAddress.setCustState(custState);
		customerAddress.setCustZipCode(custZipCode);
		customerAddress.setCustPhone(custPhone);
		
		int customerAddressAdded = customerAddressRepository.addCustomerAddress(customerId, customerAddress);
		if (customerAddressAdded > 0) {
			return "Successfully added the customer address ";
		}
		else return "Failed to add";
    	
    }
	
	@RequestMapping("/customer/defAddress/")
	 public String setCustomerDefaultAddress(HttpServletRequest request) {
		int addressId = Integer.parseInt(request.getParameter("addressId"));
		int customerUpdate = customerAddressRepository.setDefaultAddress(addressId);
		if (customerUpdate > 0) {
			return "Successful " ;
		}
		else return "Failed to Update";
	}


}
