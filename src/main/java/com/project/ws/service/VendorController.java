package com.project.ws.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.bind.annotation.RestController;

import com.project.ws.representation.VendorRepresentation;
import com.project.ws.representation.VendorRequest;
import com.project.ws.workflow.VendorActivity;

@RestController
public class VendorController {

	@Autowired
	VendorActivity vendorActivity;

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
