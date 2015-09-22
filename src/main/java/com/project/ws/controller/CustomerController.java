package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.business.domain.CustomerBO;
import com.project.ws.database.domain.Customer;
import com.project.ws.database.repository.custom.CustomerRepository;

/**
 * This is customer spring controller which has methods 
 * that specify the end points for the customer web service.
 */
@RestController
public class CustomerController {
	
	@Autowired
    private CustomerRepository customerRepository;

	/*
	 * This expose "/customer/" end point and looks for a URL parameter "id"
	 * then gets customer information with address.  
	 */
	@RequestMapping("/customer")
    public List<CustomerBO> getCustomers(HttpServletRequest request) {
		String id = request.getParameter("id");
    	return customerRepository.getCustomerWithAddressById(Long.parseLong(id));
    }
	
	/*
	 * This expose "/customer/firstLetter/" end point and looks for a URL parameter "letter"
	 * then gets customer information based on first name's first letter
	 * of the customer
	 */
	@RequestMapping("/customer/firstLetter/")
    public List<Customer> getCustomersFromFirstLetterOfName(HttpServletRequest request) {
		String letter = request.getParameter("letter");
    	return customerRepository.getCustomersByNamesFirstLetter(letter);
    }

}
