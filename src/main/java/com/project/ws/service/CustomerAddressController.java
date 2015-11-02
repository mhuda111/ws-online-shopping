package com.project.ws.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
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
import com.project.ws.workflow.CustomerAddressActivity;


@RestController
public class CustomerAddressController implements ErrorController {

	@Autowired
	CustomerAddressActivity custAddrActivity;
	
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
	
	@RequestMapping("/customeraddress/")
    public List<CustAddrRepresentation> getCustomersAddressFromId(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
    	return custAddrActivity.getAddress(customerId);
    }
	
	@RequestMapping(value="/customeraddress/add/", method=RequestMethod.POST)
    public String addCustomerAddress(@RequestBody AddressRequest request) {
		try {
			custAddrActivity.addCustomerAddress(request);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "Error while adding the address";
		}
		return "Address added successfully";
    }
	
	@RequestMapping(value="/customeraddress/update/", method=RequestMethod.PUT)
    public CustAddrRepresentation updateCustomerAddress(@RequestBody AddressRequest request) {
		CustAddrRepresentation custAddrRepresentation = new CustAddrRepresentation();
		try {
			custAddrRepresentation = custAddrActivity.updateCustomerAddress(request);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		if(custAddrRepresentation == null) {
			custAddrRepresentation = new CustAddrRepresentation();
			custAddrRepresentation.setMessage("Operation Failure");
			return custAddrRepresentation;
		}
		else
			return custAddrRepresentation;
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
