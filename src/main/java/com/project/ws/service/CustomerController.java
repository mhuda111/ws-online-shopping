package com.project.ws.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;

import com.google.common.collect.ImmutableMap;
import com.project.ws.representation.CustomerRepresentation;
import com.project.ws.representation.CustomerRequest;
import com.project.ws.workflow.CustomerActivity;

/**
 * This is customer spring controller which has methods 
 * that specify the end points for the customer web service.
 */
@RestController
public class CustomerController implements ErrorController {
	
	@Autowired
	CustomerActivity customerActivity;
	
	private static final String ERRORPATH = "/error";
	private static final String errorString = "You have received this page in ERROR. ";
	
	private static final Map<Object, String> errorMessages = ImmutableMap.<Object, String>builder()
			.put(HttpServletResponse.SC_NOT_FOUND, "The requested resource does not exist")
			.put(HttpServletResponse.SC_BAD_REQUEST, "The URI entered is incorrect. Please rectify and submit again")
			.put(HttpServletResponse.SC_GATEWAY_TIMEOUT, "Server Error. Please try later")
			.put(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Please contact the System Administrator")
			.put(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "This method is not allowed to access the resource. Please rectify your request")
			.put("Default", "Please contact the System Administrator")
			.build();
	
    @Override
    public String getErrorPath() {
        return ERRORPATH;
    }
    
	@RequestMapping(ERRORPATH)
	public @ResponseBody String error(HttpServletRequest request, HttpServletResponse response) {
		return errorString + errorMessages.get(response.getStatus());
    }
	
	@RequestMapping("/customer/firstName/")
    public CustomerRepresentation getCustomersByFirstName(HttpServletRequest request) {
		String name = request.getParameter("fname");
    	CustomerRepresentation customerRepresentation = customerActivity.getCustomersByFirstName(name);
    	if(customerRepresentation == null) throw new ResourceNotFoundException();
    	return customerRepresentation;
    }

	@RequestMapping(value="/customer/", method=RequestMethod.GET)
    public CustomerRepresentation getCustomerById(HttpServletRequest request) {
    	CustomerRepresentation customerRepresentation =  customerActivity.getCustomerById(Integer.parseInt(request.getParameter("customerId")));
    	return customerRepresentation;
    }
	
	@RequestMapping(value = "/customer/addCustomer", method=RequestMethod.POST)
    public @ResponseBody CustomerRepresentation addCustomerWithInfo(@RequestBody CustomerRequest customerRequest) {
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		try {
			 customerRepresentation = customerActivity.addCustomer(customerRequest);
		} catch(RuntimeException e) {
			System.out.println("error message " + e.getMessage());
			throw new MethodNotAllowedException();
		}
		return customerRepresentation;
    }
	
	@RequestMapping(value="/customer/", method=RequestMethod.DELETE)
    public @ResponseBody String deleteCustomer(HttpServletRequest request) {
		String message;
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		try {
			message = customerActivity.deleteCustomer(customerId);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			message = "ERROR!";
			throw new MethodNotAllowedException();
		}
		return message;
    }
	
	@RequestMapping(value="/customer/updateName", method=RequestMethod.PUT)
	 public CustomerRepresentation updateCustomerWithInfo(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		CustomerRepresentation customerRepresentation = customerActivity.updateName(customerId, firstName, lastName);
		return customerRepresentation;
	}
	
	@RequestMapping(value="/customer/updateEmail", method=RequestMethod.PUT)
	 public CustomerRepresentation updateEmail(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		CustomerRepresentation customerRepresentation = customerActivity.updateEmail(customerId, email);
		return customerRepresentation;
	}

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "Resource you are trying to access does not exist. Please check your link again";
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
