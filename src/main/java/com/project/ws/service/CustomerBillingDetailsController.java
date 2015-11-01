package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.representation.BillingRequest;
import com.project.ws.representation.CustBillingRepresentation;
import com.project.ws.workflow.CustomerBillingActivity;

@RestController
public class CustomerBillingDetailsController {

	@Autowired
    private CustomerBillingActivity billActivity;
	
	@Autowired
	CustBillingRepresentation billRepresentation;
	
	@Autowired
	BillingRequest billRequest;

	@RequestMapping(value="/billing/processPayment", method=RequestMethod.PUT)
    public CustBillingRepresentation processPayment(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		Integer billId = Integer.parseInt(request.getParameter("billId"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		String type = request.getParameter("type");
		billRepresentation = billActivity.processPayment(customerId, billId, amount, type);
		return billRepresentation;
    }

	@RequestMapping(value="/billing/", method=RequestMethod.GET)
    public List<CustBillingRepresentation> getBillingInfo(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		List<CustBillingRepresentation> billList = new ArrayList<CustBillingRepresentation>();
		billList = billActivity.getBillingDetails(customerId);
		return billList;
    }
	
	@RequestMapping(value="/billing/", method=RequestMethod.POST)
    public CustBillingRepresentation getBillingInfo(@RequestBody BillingRequest request) {
		billRepresentation = billActivity.addBillingDetails(request);
		return billRepresentation;
    }

}
