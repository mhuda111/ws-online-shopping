package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Customer;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;
import com.project.ws.workflow.CustomerActivity;

/**
 * This is customer spring controller which has methods 
 * that specify the end points for the customer web service.
 */
@RestController
public class CustomerController {
	
	@Autowired
    private CustomerActivity customerActivity;
	
	/*
	 * This expose "/customer/firstLetter/" end point and looks for a URL parameter "letter"
	 * then gets customer information based on first name's first letter
	 * of the customer
	 */
	@RequestMapping("/customer/firstLetter")
    public @ResponseBody List<CustomerRepresentation> getCustomersFromFirstLetterOfName(HttpServletRequest request) {
		String letter = request.getParameter("letter");
    	return customerActivity.getCustomersByNamesFirstLetter(letter);
    }
	
//	@RequestMapping(value="/customer/firstName/{fname}",method = RequestMethod.GET, consumes={"application/xml", "text/*", "application/xhtml+xml", "application/json", "application/html", "application/*+json"}, produces = {"text/html","application/json", "application/xml"})
//    public @ResponseBody List<CustomerRepresentation> getCustomersByFirstName(@PathVariable("fname") String name) {
//    	List<Customer> customer = customerActivity.findByCustFirstName(name);
//    	List<CustomerRepresentation> customerRepresentation = new ArrayList<CustomerRepresentation>();
//    	for(Customer c:customer) {
//    		customerRepresentation.add(customerActivity.mapRepresentation(c));
//    	}
//    	return customerRepresentation;
//    }
	
	@RequestMapping("/customer/firstName/")
    public CustomerRepresentation getCustomersByFirstName(HttpServletRequest request) {
//		System.out.println(request.getContentType());
//		System.out.println(request.getHeader("Accept"));
		String name = request.getParameter("fname");
    	List<Customer> customer = customerActivity.findByCustFirstName(name);
    	CustomerRepresentation customerRepresentation = customerActivity.mapRepresentation(customer.get(0));
    	return customerRepresentation;
    }

	@RequestMapping("/customer/")
    public CustomerRepresentation getCustomerById(HttpServletRequest request) {
    	Customer customer =  customerActivity.findByCustId(Integer.parseInt(request.getParameter("customerId")));
    	return customerActivity.mapRepresentation(customer);
    }
	
	@RequestMapping(value = "/customer/addCustomer", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody String addCustomerWithInfo(@RequestBody CustomerRequest customerRequest) {
		String firstName = customerRequest.getFirstName();
		String lastName = customerRequest.getLastName();
		String email = customerRequest.getEmail();
		String password = customerRequest.getPassword();
		int customerAdded = customerActivity.addCustomer(firstName, lastName, email, password);
		if (customerAdded > 0) {
			return "Successfully added the customer " + firstName;
		}
		else return "Failed to add";
    }
	
	@RequestMapping(value = "/customer/deleteCustomer/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteCustomer(@PathVariable String id) {
		int customerId = Integer.parseInt(id);
		try {
			int noOfDeletedRow = customerActivity.deleteCustomer(customerId);
			if (noOfDeletedRow > 0) {
				return "Deleted Successfully";
			}
		} catch (Exception ex) {
			return "ERROR!";
		}
		return "No rows found to delete";
    }
	
	@RequestMapping("/customer/updateCustomer")
	 public String updateCustomerWithInfo(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int customerUpdate = customerActivity.updateName(customerId, firstName, lastName);
		if (customerUpdate > 0) {
			return "Successfully updated the customer " + firstName;
		}
		else return "Failed to Update";
	}
	
	@RequestMapping("/customer/updateCustomerEmail/")
	 public String updateEmail(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		int customerUpdate = customerActivity.updateEmail(customerId, email);
		if (customerUpdate > 0) {
			return "Successfully updated the customer " ;
		}
		else return "Failed to Update";
	}
	
	@RequestMapping("/customer/updateCustomerPassword/")
	 public String updatePassword(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String password = request.getParameter("password");
		int customerUpdate = customerActivity.updatePassword(customerId, password);
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
		int customerUpdate = customerActivity.changeStatus(customerId, flag);
		if (customerUpdate > 0) {
			return "Status updated successfully" ;
		}
		else return "Failed to Update Status";
	}

}
