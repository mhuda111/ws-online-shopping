package com.project.ws.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.repository.customer.billing.CustomerBillingRepository;

@RestController
public class CustomerBillingDetailsController {

	@Autowired
    private CustomerBillingRepository customerBillingsRepository;

	@RequestMapping("/billing/chargeCard")
    public String chargeCard(HttpServletRequest request) {
		int idcustomerId = Integer.parseInt(request.getParameter("customerId"));
		int billId = Integer.parseInt(request.getParameter("billId"));
		Double amount = Double.parseDouble(request.getParameter("amount"));
		int card = customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
		if (card>0) {
			return "Successfully charged " + amount;
		}
		else return "Denied";
		//return customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
    }

	@RequestMapping("/billing/updateAddress")
    public String updateBillingAddress(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		String addrLine1 = request.getParameter("addrLine1");
		String addrLine2 = request.getParameter("addrLine2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");

		int updateBill = customerBillingsRepository.updateBillingAddress(customerId, addrLine1, addrLine2,city,state,zip);
		if (updateBill>0) {
			return "Successful update billing address";
		}
		else return "Denied";
		//return customerBillingsRepository.chargeCard(idcustomerId, billId, amount);
    }

}
