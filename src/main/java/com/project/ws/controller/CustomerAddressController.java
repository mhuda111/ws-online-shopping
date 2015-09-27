package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.CustomerAddress;
import com.project.ws.database.repository.custom.CustomerAddressRepository;


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
//	@RequestMapping("/customeraddress/id/")
//    public List<CustomerAddress> getCustomersAddressByCustId(HttpServletRequest request) {
//		int id = Integer.parseInt(request.getParameter("id"));
//		//String id = request.getParameter("id");
//    	return customerAddressRepository.getCustomersAddressByCustId(id);
//    }


}
