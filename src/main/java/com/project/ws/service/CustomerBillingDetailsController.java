	package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.representation.BillingRequest;
import com.project.ws.representation.CustBillingRepresentation;
import com.project.ws.representation.StringRepresentation;
import com.project.ws.workflow.CustomerActivity;
import com.project.ws.workflow.CustomerBillingActivity;

@RestController
public class CustomerBillingDetailsController {

	@Autowired
    CustomerBillingActivity billActivity;
	
	@Autowired
	CustomerActivity customerActivity;
	
	@Autowired
	CustBillingRepresentation billRepresentation;
	
	@Autowired
	BillingRequest billRequest;
	
	/*
	 * PUT to process a customer payment for an order
	 */
	@RequestMapping(value="/billing/processPayment", method=RequestMethod.PUT, params={"customerId", "billId", "amount"})
    public StringRepresentation processPayment(HttpServletRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		Integer billId = Integer.parseInt(request.getParameter("billId"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		String type = "Debit";
		stringRepresentation = billActivity.processPayment(customerId, billId, amount, type);
		return stringRepresentation;
    }

	/*
	 * GET to retrieve customer billing details by customerId
	 */
	@RequestMapping(value="/billing/", method=RequestMethod.GET, params="customerId")
    public List<CustBillingRepresentation> getBillingInfo(HttpServletRequest request) {
		List<CustBillingRepresentation> billList = new ArrayList<CustBillingRepresentation>();
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		billList = billActivity.getBillingDetails(customerId);
		return billList;
    }
	
	/*
	 * DELETE to delete customer billing details by billingId
	 */
	@RequestMapping(value="/billing/", method=RequestMethod.DELETE, params="billingId")
    public StringRepresentation deleteBilling(HttpServletRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer billingId = Integer.parseInt(request.getParameter("billingId"));
		stringRepresentation = billActivity.deleteBillingInfo(billingId);
		return stringRepresentation;
    }
	
	/*
	 * POST to add new billing details for a customer using billing Request
	 */
	@RequestMapping(value="/billing/", method=RequestMethod.POST)
    public StringRepresentation getBillingInfo(@RequestBody BillingRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer customerId = request.getCustomerId();
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		stringRepresentation = billActivity.addBillingDetails(request);
		return stringRepresentation;
    }

}
