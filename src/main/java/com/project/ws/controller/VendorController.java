package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.Vendor;
import com.project.ws.database.repository.custom.VendorRepository;

@RestController
public class VendorController {
		
		@Autowired
	    private VendorRepository vendorRepository;

		/*
		 * This expose "/customer/" end point and looks for a URL parameter "id"
		 * then gets customer information with address.  
		 */
//		@RequestMapping("/customer")
//	    public List<CustomerBO> getCustomers(HttpServletRequest request) {
//			String id = request.getParameter("id");
//	    	return customerRepository.getCustomerWithAddressById(Long.parseLong(id));
//	    }
		
		/*
		 * This expose "/customer/firstLetter/" end point and looks for a URL parameter "letter"
		 * then gets customer information based on first name's first letter
		 * of the customer
		 */
		@RequestMapping("/vendor/firstLetter/")
	    public List<Vendor> getVendorByNamesFirstLetter(HttpServletRequest request) {
			String letter = request.getParameter("letter");
	    	return vendorRepository.getVendorByNamesFirstLetter(letter);
	    }

	
}
