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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.representation.VendorRepresentation;
import com.project.ws.representation.VendorRequest;
import com.project.ws.workflow.VendorActivity;

@RestController
public class VendorController {

	@Autowired
	VendorActivity vendorActivity;
	
	@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		ex.printStackTrace();
        return "Error: " + message + " : " + req.getRequestURI() + ".\n\n OR Please contact the system administrator ";
    }

	@RequestMapping(value="/vendor", method=RequestMethod.GET, params="vendorId")
	public @ResponseBody VendorRepresentation getVendorById(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
		if(vendorActivity.validateVendor(vendorId) == false) 
			throw new VendorNotFoundException(vendorId);
		vendorRepresentation =  vendorActivity.getVendor(vendorId);
		return vendorRepresentation;
	}

	@RequestMapping(value="/vendor/name", method=RequestMethod.GET, params="vendorName")
	public @ResponseBody VendorRepresentation getVendorByName(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		String name = request.getParameter("vendorName");
		vendorRepresentation =  vendorActivity.getVendor(name);
		if(vendorRepresentation == null)
			throw new VendorNotFoundException(name);
		return vendorRepresentation;
	}
	
	@RequestMapping(value="/vendor/addVendor", method=RequestMethod.POST)
	public @ResponseBody VendorRepresentation addVendor(@RequestBody VendorRequest request, HttpServletResponse response) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		vendorRepresentation =  vendorActivity.addVendor(request);
		return vendorRepresentation;
	}

	@RequestMapping(value="/vendor/", method=RequestMethod.PUT, params={"vendorId", "vendorName"})
	public @ResponseBody VendorRepresentation updateVendorName(HttpServletRequest request, HttpServletResponse response) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		String vendorName = request.getParameter("vendorName");
		Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
		if(vendorActivity.validateVendor(vendorId) == false) 
			throw new VendorNotFoundException(vendorId);
		vendorRepresentation = vendorActivity.updateName(vendorId, vendorName);
		return vendorRepresentation;
	}
	
	@RequestMapping(value="/vendor", method=RequestMethod.DELETE)
	public @ResponseBody String deleteVendor(HttpServletRequest request) {
		Integer count = 0;
		Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
		if(vendorActivity.validateVendor(vendorId) == false) 
			throw new VendorNotFoundException(vendorId);
		count = vendorActivity.deleteVendor(vendorId);
		if(count == 1)
			return "Vendor Deleted Successfully";
		else
			return "Error deleting vendor. Please check the logs.";
	}
	
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class VendorNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5356641914207234841L;

	public VendorNotFoundException(Object vendor) {
		super("Could not find vendor - " + vendor);
	}
}

