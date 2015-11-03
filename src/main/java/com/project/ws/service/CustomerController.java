package com.project.ws.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

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
	CustomerActivity customerActivity;
	
	@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		return "Error: " + message + " : " + req.getRequestURI() + ".\n\n Please contact the system administrator ";
    }
	
	@RequestMapping(value="/customer", method=RequestMethod.GET, params="fname")
    public CustomerRepresentation getCustomersByFirstName(HttpServletRequest request) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		String name = request.getParameter("fname");
		customerRepresentation = customerActivity.getCustomersByFirstName(name);
		if(customerRepresentation == null)
			throw new CustomerNotFoundException(name);
    	return customerRepresentation;
    }

	@RequestMapping(value="/customer", method=RequestMethod.GET)
    public CustomerRepresentation getCustomerById(HttpServletRequest request) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		customerRepresentation =  customerActivity.getCustomerById(customerId);
    	return customerRepresentation;
    }
	
	@RequestMapping(value = "/customer/addCustomer", method=RequestMethod.POST)
    public @ResponseBody CustomerRepresentation addCustomerWithInfo(@RequestBody CustomerRequest customerRequest) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		customerRepresentation = customerActivity.addCustomer(customerRequest);
		return customerRepresentation;
    }
	
	@RequestMapping(value="/customer", method=RequestMethod.DELETE)
    public @ResponseBody String deleteCustomer(HttpServletRequest request) {
		String message;
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		message = customerActivity.deleteCustomer(customerId);
		return message;
    }
	
	@RequestMapping(value="/customer", method=RequestMethod.PUT, params={"customerId","firstName", "lastName" })
	 public CustomerRepresentation updateCustomerWithInfo(HttpServletRequest request) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		customerRepresentation = customerActivity.updateName(customerId, firstName, lastName);
		return customerRepresentation;
	}
	
	@RequestMapping(value="/customer", method=RequestMethod.PUT, params={"customerId", "email"})
	 public CustomerRepresentation updateEmail(HttpServletRequest request) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		customerRepresentation = customerActivity.updateEmail(customerId, email);
		return customerRepresentation;
	}

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7531082778174131155L;
	public ResourceNotFoundException() {
	    super("Resource you are trying to access does not exist. Please check your link again");
	}
}

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class ServerErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ServerErrorException() {
		super("Server is Not Responding Currently. Please try again later");
	}
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class PageNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PageNotFoundException() {
		super("The link you are trying to access does not exist. Please check it again");
	}
}

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
class MethodNotAllowedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MethodNotAllowedException() {
		super("The method mapped via the url is not allowed. Please contact the System Administrator");
	}
}
