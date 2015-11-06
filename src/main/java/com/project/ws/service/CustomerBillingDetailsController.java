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
    public CustBillingRepresentation processPayment(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		Integer billId = Integer.parseInt(request.getParameter("billId"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		String type = request.getParameter("type");
		billRepresentation = billActivity.processPayment(customerId, billId, amount, type);
		return billRepresentation;
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
	 * POST to add new billing details for a customer using billing Request
	 */
	@RequestMapping(value="/billing/", method=RequestMethod.POST)
    public CustBillingRepresentation getBillingInfo(@RequestBody BillingRequest request) {
		Integer customerId = request.getCustomerId();
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		billRepresentation = billActivity.addBillingDetails(request);
		return billRepresentation;
    }

}
