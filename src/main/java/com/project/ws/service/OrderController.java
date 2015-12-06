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
import org.springframework.http.MediaType;
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
import com.project.ws.workflow.VendorActivity;

@RestController
public class OrderController {
	
	@Autowired
    private OrderActivity orderActivity;
	
	@Autowired
	private ProductActivity productActivity;
	
	@Autowired
	private CustomerActivity customerActivity;
	
	@Autowired
	private VendorActivity vendorActivity;

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
	 * GET to retrieve a given order
	 */
	@RequestMapping(value="/order/view", method=RequestMethod.GET, params="orderId")
	public @ResponseBody OrderRepresentation findOneOrder(HttpServletRequest request) {
		Integer orderId = 0;
		OrderRepresentation orderRepr = new OrderRepresentation();
		orderId = Integer.parseInt(request.getParameter("orderId"));
		orderRepr = orderActivity.findOneOrder(orderId);
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
	 public @ResponseBody StringRepresentation placeOrder(HttpServletRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		if(customerActivity.validateCustomer(customerId) == false)
			throw new CustomerNotFoundException(customerId);
		stringRepresentation = orderActivity.placeOrder(customerId);
		return stringRepresentation;
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
	 public @ResponseBody StringRepresentation cancelOrder(HttpServletRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		if(orderActivity.validateOrder(orderId) == false)
			throw new OrderNotFoundException(orderId);
		stringRepresentation = orderActivity.cancelOrder(orderId);
		return stringRepresentation;
	}
	
	/*
	 * GET to check order status using orderId
	 */
	@RequestMapping(value="/order/checkOrderStatus", method=RequestMethod.GET, params="orderId", produces = {"application/json"})
	public @ResponseBody OrderRepresentation checkOrderStatus(HttpServletRequest request) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		if(orderActivity.validateOrder(orderId) == false)
			throw new OrderNotFoundException(orderId);
		orderRepresentation = orderActivity.checkOrderStatus(orderId);
		return orderRepresentation; 
	}
	
	/*
	 * GET to retrieve all orders by customerId
	 */
	@RequestMapping(value="/order/viewActiveOrdersForVendor", method=RequestMethod.GET, params="vendorId")
	public @ResponseBody List<OrderRepresentation> findAllActiveOrdersForVendor(HttpServletRequest request) {
		Integer vendorId = 0;
		List<OrderRepresentation> orderRepr = new ArrayList<OrderRepresentation>();
		vendorId = Integer.parseInt(request.getParameter("vendorId"));
		if(vendorActivity.validateVendor(vendorId) == false)
			throw new VendorIdNotFoundException(vendorId);
		orderRepr = orderActivity.findAllOrdersForVendor(vendorId, "ACT");
		return orderRepr;
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

@ResponseStatus(HttpStatus.NOT_FOUND)
class VendorIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5356641914207234841L;

	public VendorIdNotFoundException(Object vendor) {
		super("Could not find vendor - " + vendor);
	}
}


