package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.Customer;
import com.project.ws.database.repository.customer.CustomerRepository;

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
	@RequestMapping("/customer/firstLetter/")
    public List<Customer> getCustomersFromFirstLetterOfName(HttpServletRequest request) {
		String letter = request.getParameter("letter");
    	return customerRepository.getCustomersByNamesFirstLetter(letter);
    }
	
	@RequestMapping("/customer/addCustomer/")
    public String addCustomerWithInfo(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int customerAdded = customerRepository.addCustomer(firstName, lastName, email, password);
		if (customerAdded > 0) {
			return "Successfully added the customer " + firstName;
		}
		else return "Failed to add";
    	
    }
	
	@RequestMapping("/customer/updateCustomer/")
	 public String updateCustomerWithInfo(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int customerUpdate = customerRepository.updateName(customerId, firstName, lastName);
		if (customerUpdate > 0) {
			return "Successfully updated the customer " + firstName;
		}
		else return "Failed to Update";
	}
	
	@RequestMapping("/customer/updateCustomerEmail/")
	 public String updateEmail(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		int customerUpdate = customerRepository.updateEmail(customerId, email);
		if (customerUpdate > 0) {
			return "Successfully updated the customer " ;
		}
		else return "Failed to Update";
	}
	
	@RequestMapping("/customer/updateCustomerPassword/")
	 public String updatePassword(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String password = request.getParameter("password");
		int customerUpdate = customerRepository.updatePassword(customerId, password);
		if (customerUpdate > 0) {
			return "Successfully updated the password" ;
		}
		else return "Failed to Update password";
	}
	
	@RequestMapping("/customer/updateCustomerStatus/")
	 public String changeStatus(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		char flag = request.getParameter("flag").charAt(0);
		
		//char flag = request.getParameter("flag");
		int customerUpdate = customerRepository.changeStatus(customerId, flag);
		if (customerUpdate > 0) {
			return "Status updated successfully" ;
		}
		else return "Failed to Update Status";
	}

}
