package com.project.ws.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Vendor;
import com.project.ws.workflow.VendorActivity;

@RestController
public class VendorController {

	@Autowired
	private VendorActivity vendorRepository;

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


	@RequestMapping(value = "/vendor/deleteVendor/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteCustomer(@PathVariable String id) {
		int vendorId = Integer.parseInt(id);
		try {
			int noOfDeletedRow = vendorRepository.deleteVendor(vendorId);
			if (noOfDeletedRow > 0) {
				return "Deleted Successfully";
			}
		} catch (Exception ex) {
			return "ERROR!";
		}
		return "No rows found to delete";
    }

	@RequestMapping("/vendor/updateVendorStatus/")
	 public String changeStatus(HttpServletRequest request) {
		int vendorId = Integer.parseInt(request.getParameter("vendorId"));
		String flag = request.getParameter("flag");

		//char flag = request.getParameter("flag");
		int vendorrUpdate = vendorRepository.changeStatus(vendorId, flag);
		if (vendorrUpdate > 0) {
			return "Status updated successfully" ;
		}
		else return "Failed to Update Status";
	}

	@RequestMapping("/vendor/updateAddress")
    public String updateAddress(HttpServletRequest request) {
		String vendor_addr_line1 = request.getParameter("vendor_addr_line1");
		String vendor_addr_line2 = request.getParameter("vendor_addr_line2");
		String vendor_city = request.getParameter("vendor_city");
		String vendorState = request.getParameter("state");
		String vendor_zip_code = request.getParameter("vendor_zip_code");
		String vendor_country = request.getParameter("vendorCountry");
		int vendorId = Integer.parseInt(request.getParameter("vendorId"));

		Vendor vendor = new Vendor();
		vendor.setVendorAddrLine1(vendor_addr_line1);
		vendor.setVendorAddrLine2(vendor_addr_line2);
		vendor.setVendorCity(vendor_city);
		vendor.setVendorState(vendorState);
		vendor.setVendorZipCode(vendor_zip_code);
		vendor.setVendorCountry(vendor_country);
		vendor.setVendorId(vendorId);

		int updateVendorAddress = vendorRepository.updateAddress(vendor);
		if (updateVendorAddress>0) {
			return "Successful update vendor address";
		}
		else return "Denied";
		//return customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
    }



	@RequestMapping("/vendor/settleAccount/")
	public String settleAccount(HttpServletRequest request) {
		int vendorId = Integer.parseInt(request.getParameter("vendorId"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		String paymentType = request.getParameter("paymentType");
		int vendorSettled = vendorRepository.settleAccount(vendorId,amount, paymentType);
		if (vendorSettled > 0) {
			return "Successfully settle vendor account ";
		}
		return "Failed";
	}



}
