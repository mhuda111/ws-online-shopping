package com.project.ws.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.repository.custom.CustomerBillingRepository;

@RestController
public class CustomerBillingDetailsController {

	@Autowired
    private CustomerBillingRepository customerBillingsRepository;

	@RequestMapping("/billing")
    public String chargeCard(HttpServletRequest request) {
		int idcustomerId = Integer.parseInt(request.getParameter("customerId"));
		int billId = Integer.parseInt(request.getParameter("billId"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		int card = customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
		if (card>0) {
			return "Successful";
		}
		else return "Denied";
		//return customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
    }

}
