package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Cart;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.StringRepresentation;
import com.project.ws.representation.CartRequest;
import com.project.ws.workflow.CustomerActivity;
import com.project.ws.workflow.OrderActivity;
import com.project.ws.workflow.ProductActivity;

@RestController
public class OrderController {
	
	@Autowired
    private OrderActivity orderActivity;
	
	@Autowired
	private ProductActivity productActivity;
	
	@Autowired
	private CustomerActivity customerActivity;

	/*
	 * GET to retrieve all orders by customerId
	 */
	@RequestMapping(value="/order", method=RequestMethod.GET, params="customerId")
	public @ResponseBody List<OrderRepresentation> findAllOrders(HttpServletRequest request) {
		Integer customerId = 0;
		List<OrderRepresentation> orderRepr = new ArrayList<OrderRepresentation>();
		customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		orderRepr = orderActivity.allOrders(customerId, "all");
		return orderRepr;
    }

	/*
	 * GET to retrieve all Active orders by customerId
	 */
	@RequestMapping(value="/order/activeOrders", method=RequestMethod.GET, params="customerId")
    public @ResponseBody List<OrderRepresentation> findAllActiveOrders(HttpServletRequest request) {
		Integer customerId;
		List<OrderRepresentation> orderRepr = new ArrayList<OrderRepresentation>();
		customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		orderRepr = orderActivity.allOrders(customerId, "active");
		return orderRepr;
    }

	/*
	 * PUT to place an order using customerId
	 */
	@RequestMapping(value="/order/placeOrder", method=RequestMethod.PUT, params="customerId")
	 public @ResponseBody OrderRepresentation placeOrder(HttpServletRequest request) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		orderRepresentation = orderActivity.placeOrder(customerId);
		return orderRepresentation;
	}
	
	/*
	 * PUT to ship an order using orderId
	 */
	@RequestMapping(value="/order/ship", method=RequestMethod.PUT, params="orderId")
	 public @ResponseBody OrderRepresentation shipOrder(HttpServletRequest request) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		System.out.println("in controller " + orderId);
		if(orderActivity.validateOrder(orderId) == false)
			throw new OrderNotFoundException(orderId);
		orderRepresentation = orderActivity.shipOrder(orderId);
		return orderRepresentation;
	}
	
	/*
	 * PUT to cancel and order using orderId
	 */
	@RequestMapping(value="/order/cancelOrder", method=RequestMethod.PUT, params="orderId")
	 public @ResponseBody OrderRepresentation cancelOrder(HttpServletRequest request) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		if(orderActivity.validateOrder(orderId) == false)
			throw new OrderNotFoundException(orderId);
		orderRepresentation = orderActivity.cancelOrder(orderId);
		return orderRepresentation;
	}
	
	/*
	 * GET to check order status using orderId
	 */
	@RequestMapping(value="/order/checkOrderStatus", method=RequestMethod.GET, params="orderId")
	public @ResponseBody OrderRepresentation checkOrderStatus(HttpServletRequest request) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		if(orderActivity.validateOrder(orderId) == false)
			throw new OrderNotFoundException(orderId);
		orderRepresentation = orderActivity.checkOrderStatus(orderId);
		return orderRepresentation; 
	}

}

@ResponseStatus(value=HttpStatus.NOT_FOUND)
class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6919160978660692882L;

	public CustomerNotFoundException(Object customer) {
		super("Could not find customer- " + customer);
	}
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class OrderNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4064641490641085268L;

	public OrderNotFoundException(Integer orderId) {
		super("Could not find order - " + orderId);
	}
}


