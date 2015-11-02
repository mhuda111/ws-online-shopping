package com.project.ws.service;

import java.util.ArrayList;
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
import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.representation.BillingRequest;
import com.project.ws.representation.CustBillingRepresentation;
import com.project.ws.workflow.CustomerBillingActivity;

@RestController
public class CustomerBillingDetailsController implements ErrorController {

	@Autowired
    private CustomerBillingActivity billActivity;
	
	@Autowired
	CustBillingRepresentation billRepresentation;
	
	@Autowired
	BillingRequest billRequest;
	
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
