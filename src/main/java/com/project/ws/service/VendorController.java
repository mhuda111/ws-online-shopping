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
        return "Error: " + message + " in path: " + req.getRequestURI();
    }

	@RequestMapping(value="/vendor/", method=RequestMethod.GET, params="vendorId")
	public @ResponseBody VendorRepresentation getVendorById(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		try {
			Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
			if(vendorActivity.validateVendor(vendorId) == false) 
				throw new VendorNotFoundException(vendorId);
			vendorRepresentation =  vendorActivity.getVendor(vendorId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return vendorRepresentation;
	}

	@RequestMapping(value="/vendor/", method=RequestMethod.GET, params="vendorName")
	public @ResponseBody VendorRepresentation getVendorByName(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		try {
			String name = request.getParameter("vendorName");
			vendorRepresentation =  vendorActivity.getVendor(name);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return vendorRepresentation;
	}
	
	@RequestMapping(value="/vendor/addVendor/", method=RequestMethod.POST)
	public @ResponseBody VendorRepresentation addVendor(@RequestBody VendorRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		try {
			if(vendorActivity.validateVendor(request.getVendorId()) == false) 
				throw new VendorNotFoundException(request.getVendorId());
			vendorRepresentation =  vendorActivity.addVendor(request);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return vendorRepresentation;
	}

	@RequestMapping(value="/vendor/", method=RequestMethod.PUT, params={"vendorId", "vendorName"})
	public @ResponseBody VendorRepresentation updateVendorName(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		try {
			String vendorName = request.getParameter("vendorName");
			Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
			if(vendorActivity.validateVendor(vendorId) == false) 
				throw new VendorNotFoundException(vendorId);
			vendorRepresentation = vendorActivity.updateName(vendorId, vendorName);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return vendorRepresentation;
	}
	
	@RequestMapping(value="/vendor/delete", method=RequestMethod.DELETE)
	public @ResponseBody VendorRepresentation logicalDeleteVendor(HttpServletRequest request) {
		VendorRepresentation vendorRepresentation = new VendorRepresentation();
		try {
			Integer vendorId = Integer.parseInt(request.getParameter("vendorId"));
			if(vendorActivity.validateVendor(vendorId) == false) 
				throw new VendorNotFoundException(vendorId);
			vendorRepresentation = vendorActivity.logicalDeleteVendor(vendorId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return vendorRepresentation;
	}
	
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class VendorNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5356641914207234841L;

	public VendorNotFoundException(Integer vendorId) {
		super("Could not find vendor - " + vendorId);
	}
}
