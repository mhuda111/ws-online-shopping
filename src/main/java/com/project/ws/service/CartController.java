package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.CartRequest;
import com.project.ws.representation.StringRepresentation;
import com.project.ws.workflow.CartActivity;
import com.project.ws.workflow.CustomerActivity;
import com.project.ws.workflow.OrderActivity;
import com.project.ws.workflow.ProductActivity;

@RestController
public class CartController {

	@Autowired
	private CartActivity cartActivity;
	
	@Autowired
	private CustomerActivity customerActivity;
	
	/*
	 * POST to create new order using CartRequest
	 */
	@RequestMapping(value="/cart/add", method=RequestMethod.POST)
	public @ResponseBody StringRepresentation createOrder(@RequestBody CartRequest cartRequest) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		if(customerActivity.validateCustomer(cartRequest.getCustomerId()) == false)
			throw new CustomerNotFoundException(cartRequest.getCustomerId());
		stringRepresentation = cartActivity.buyProduct(cartRequest);
		return stringRepresentation;
	}
	
	/*
	 * GET to view Cart
	 */
	@RequestMapping(value="/cart/view", method=RequestMethod.GET, params="customerId")
	public @ResponseBody List<CartRepresentation> viewCart(HttpServletRequest request) {
		List<CartRepresentation> cartList = new ArrayList<CartRepresentation>();
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		cartList = cartActivity.viewCart(customerId);
		return cartList;
	}
	
	/*
	 * PUT to update billing details in Cart
	 */
	@RequestMapping(value="/cart/selectPayment", method=RequestMethod.PUT, params={"customerId", "billId"})
	public @ResponseBody StringRepresentation selectPayment(HttpServletRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		Integer billId = Integer.parseInt(request.getParameter("billId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		stringRepresentation = cartActivity.updateCart(customerId, billId);
		return stringRepresentation;
	}
}
