package com.project.ws.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.representation.VendorRepresentation;
import com.project.ws.representation.VendorRequest;
import com.project.ws.workflow.VendorActivity;

@RestController
public class VendorController implements ErrorController {

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

	@Autowired
	VendorActivity vendorActivity;
	
    @Override
    public String getErrorPath() {
        return ERRORPATH;
    }
    
	@RequestMapping(ERRORPATH)
	public @ResponseBody String error(HttpServletRequest request, HttpServletResponse response) {
		return errorString + errorMessages.get(response.getStatus());
    }

	@RequestMapping(value="/vendor/", method=RequestMethod.GET, params="vendorId")
	public @ResponseBody VendorRepresentation getVendorById(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
		vendorRepresentation =  vendorActivity.getVendor(vendorId);
		return vendorRepresentation;
	}

	@RequestMapping(value="/vendor/", method=RequestMethod.GET, params="vendorName")
	public @ResponseBody VendorRepresentation getVendorByName(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		String name = request.getParameter("vendorName");
		vendorRepresentation =  vendorActivity.getVendor(name);
		return vendorRepresentation;
	}
	
	@RequestMapping(value="/vendor/addVendor/", method=RequestMethod.POST)
	public @ResponseBody VendorRepresentation addVendor(@RequestBody VendorRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		vendorRepresentation =  vendorActivity.addVendor(request);
		return vendorRepresentation;
	}

	@RequestMapping(value="/vendor/", method=RequestMethod.PUT, params={"vendorId", "vendorName"})
	public @ResponseBody VendorRepresentation updateVendorName(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		String vendorName = request.getParameter("vendorName");
		Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
		vendorRepresentation = vendorActivity.updateName(vendorId, vendorName);
		return vendorRepresentation;
	}
	
	@RequestMapping(value="/vendor/delete", method=RequestMethod.DELETE)
	public @ResponseBody VendorRepresentation logicalDeleteVendor(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
		vendorRepresentation = vendorActivity.logicalDeleteVendor(vendorId);
		return vendorRepresentation;
	}

//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleResourceNotFoundException() {
//        return "Some server error. Please contact the System Administrator";
//    }
	
}
