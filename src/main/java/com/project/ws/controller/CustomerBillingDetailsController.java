package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.CustomerBillingDetails;
import com.project.ws.database.repository.custom.CustomerBillingDetailsRepository;

@RestController
public class CustomerBillingDetailsController {
	
	@Autowired
    private CustomerBillingDetailsRepository customerBillingDetailsRepository;

	/*
	 * This expose "/customer/" end point and looks for a URL parameter "id"
	 * then gets customer information with address.  
	 */

	/*
	 * This expose "/customer/firstLetter/" end point and looks for a URL parameter "letter"
	 * then gets customer information based on first name's first letter
	 * of the customer
	 */
	@RequestMapping("/billing")
    public List<CustomerBillingDetails> getCardNameByBillingId(HttpServletRequest request) {
		String cardId = request.getParameter("cardId");
		return customerBillingDetailsRepository.getCardNameByBillId(Long.parseLong(cardId));
    	
    }

}
