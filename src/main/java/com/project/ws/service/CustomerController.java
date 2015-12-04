package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;
import com.project.ws.representation.CustomerUpdateRequest;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.StringRepresentation;
import com.project.ws.workflow.CustomerActivity;

/**
 * This is customer spring controller which has methods 
 * that specify the end points for the customer web service.
 */
@RestController
public class CustomerController {
	
	@Autowired
	CustomerActivity customerActivity;
	
	/*
	 * GET customer by customer email and password
	 */
	@RequestMapping(value="/customer/login", method=RequestMethod.POST)
    public CustomerRepresentation authenticateCustomer(@RequestBody CustomerRequest customerRequest) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		String email = customerRequest.getEmail();
		String password = customerRequest.getPassword();
		System.out.println("email is " + email);
		System.out.println("password is " + password);
		customerRepresentation =  customerActivity.authenticateCustomer(email, password);
		if(customerRepresentation == null) 
			throw new CustomerNotFoundException(email);
    	return customerRepresentation;
    }
	
	/*
	 * GET to retrieve all customer details
	 */
	@RequestMapping(value="/customers", method=RequestMethod.GET)
    public List<CustomerRepresentation> getAllCustomers(HttpServletRequest request) {
		List<CustomerRepresentation> customerRepresentations = new ArrayList<CustomerRepresentation>();
		customerRepresentations = customerActivity.getAllCustomers();
    	return customerRepresentations;
    }
	
	
	/*
	 * GET customer by first name
	 */
	@RequestMapping(value="/customer", method=RequestMethod.GET, params="fname")
    public CustomerRepresentation getCustomersByFirstName(HttpServletRequest request) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		String name = request.getParameter("fname");
		customerRepresentation = customerActivity.getCustomersByFirstName(name);
		if(customerRepresentation == null)
			throw new CustomerNotFoundException(name);
    	return customerRepresentation;
    }

	/*
	 * GET customer by customerId
	 */
	@RequestMapping(value="/customer", method=RequestMethod.GET, params="customerId")
    public CustomerRepresentation getCustomerById(HttpServletRequest request) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		String param = request.getParameter("customerId");
		Integer customerId = Integer.parseInt(param);
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		customerRepresentation =  customerActivity.getCustomerById(customerId);
    	return customerRepresentation;
    }
	
	/*
	 * POST to add new customer using Customer Request
	 */
	@RequestMapping(value = "/customer/addCustomer", method=RequestMethod.POST)
    public @ResponseBody CustomerRepresentation addCustomerWithInfo(@RequestBody CustomerRequest customerRequest) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		customerRepresentation = customerActivity.addCustomer(customerRequest);
		return customerRepresentation;
    }
	
	/*
	 * DELETE to delete a customer using customerId
	 */
	@RequestMapping(value="/customer", method=RequestMethod.DELETE, params="customerId")
    public @ResponseBody StringRepresentation deleteCustomer(HttpServletRequest request) {
		StringRepresentation message;
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		message = customerActivity.deleteCustomer(customerId);
		return message;
    }
	
	/*
	 * PUT to update customer first and last name using customerId
	 */
	@RequestMapping(value="/customer/", method=RequestMethod.PUT, params={"customerId","firstName", "lastName" })
	 public StringRepresentation updateCustomerWithInfo(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		return customerActivity.updateName(customerId, firstName, lastName);
	}
	
	/*
	 * PUT to update customer email using PUT
	 */
	@RequestMapping(value="/customer/", method=RequestMethod.PUT, params={"customerId", "email"})
	 public StringRepresentation updateEmail(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		return customerActivity.updateEmail(customerId, email);
	}
	
	/*
	 * Update customer using PUT
	 */
	@RequestMapping(value="/customer/updateCustomer", method=RequestMethod.PUT)
	 public StringRepresentation updateEmail(@RequestBody CustomerRequest customerRequest) {
		int customerId = customerRequest.getCustomerId();
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		return customerActivity.updateCustomer(customerRequest);
	}

}

