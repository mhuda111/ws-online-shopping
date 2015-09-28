package com.project.ws.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.Vendor;
import com.project.ws.database.repository.vendor.VendorRepository;

@RestController
public class VendorController {

	@Autowired
	private VendorRepository vendorRepository;

	/*
	 * This expose "/customer/firstLetter/" end point and looks for a URL parameter "letter"
	 * then gets customer information based on first name's first letter
	 * of the customer
	 */
	@RequestMapping("/vendor/addVendor/")
	public String addVendor(HttpServletRequest request) {
		String vendorName = request.getParameter("vendorName");
		String vendor_addr_line1 = request.getParameter("vendor_addr_line1");
		String vendor_addr_line2 = request.getParameter("vendor_addr_line2");
		String vendor_city = request.getParameter("vendor_city");
		String vendorState = request.getParameter("state");
		String vendor_zip_code = request.getParameter("vendor_zip_code");
		Vendor vendor = new Vendor();
		vendor.setVendorName(vendorName);
		vendor.setVendorAddrLine1(vendor_addr_line1);
		vendor.setVendorAddrLine2(vendor_addr_line2);
		vendor.setVendorCity(vendor_city);
		vendor.setVendorState(vendorState);
		vendor.setVendorZipCode(vendor_zip_code);
		int vendorAdded = vendorRepository.addVendor(vendor);
		if (vendorAdded > 0) {
			return "Successfully added vendor " + vendor.getVendorName();
		}
		return "Failed";
	}


	@RequestMapping("/vendor/settleSelletAccount/")
	public String settleAccount(HttpServletRequest request) {
		int vendorId = Integer.parseInt(request.getParameter("vendorId"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		int vendorSettled = vendorRepository.settleAccount(vendorId,amount);
		if (vendorSettled > 0) {
			return "Successfully settle vendor account ";
		}
		return "Failed";
	}


}
