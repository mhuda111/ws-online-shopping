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
import com.project.ws.representation.OrderRequest;
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

	@RequestMapping(value="/order", method=RequestMethod.GET, params="customerId")
	public @ResponseBody List<OrderRepresentation> findAllOrders(HttpServletRequest request) {
		Integer customerId = 0;
		List<OrderRepresentation> orderRepr = new ArrayList<OrderRepresentation>();
		try {
			customerId = Integer.parseInt(request.getParameter("customerId"));
			if(customerActivity.validateCustomer(customerId) == false)
				throw new CustomerNotFoundException(customerId);
			orderRepr = orderActivity.allOrders(customerId, "all");
		} catch(RuntimeException ex) {
			throw new RuntimeException();
		}
		return orderRepr;
    }
	
	@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
        return "Error: " + message + " in path: " + req.getRequestURI();
    }	

	@RequestMapping(value="/order/activeOrders", method=RequestMethod.GET, params="customerId")
    public @ResponseBody List<OrderRepresentation> findAllActiveOrders(HttpServletRequest request) {
		Integer customerId;
		List<OrderRepresentation> orderRepr = new ArrayList<OrderRepresentation>();
		try {
			customerId = Integer.parseInt(request.getParameter("customerId"));
			if(customerActivity.validateCustomer(customerId) == false)
				throw new CustomerNotFoundException(customerId);
			orderRepr = orderActivity.allOrders(customerId, "active");
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return orderRepr;
    }
	
	@RequestMapping(value="/order/createOrder", method=RequestMethod.POST)
	public @ResponseBody List<CartRepresentation> createOrder(@RequestBody Cart cart) {
		List<CartRepresentation> cartRepresentation = new ArrayList<CartRepresentation>();
		try {
			if(customerActivity.validateCustomer(cart.getCustomerId()) == false)
				throw new CustomerNotFoundException(cart.getCustomerId());
			cartRepresentation = productActivity.buyProduct(cart);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return cartRepresentation;
	}

	@RequestMapping(value="/order/placeOrder", method=RequestMethod.PUT)
	 public @ResponseBody OrderRepresentation placeOrder(@RequestBody OrderRequest orderRequest) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		try {
			if(customerActivity.validateCustomer(orderRequest.getCustomerId()) == false)
				throw new CustomerNotFoundException(orderRequest.getCustomerId());
			orderActivity.placeOrder(orderRequest.getCustomerId());
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return orderRepresentation;
	}
	
	@RequestMapping(value="/order/cancelOrder", method=RequestMethod.PUT, params="orderId")
	 public @ResponseBody OrderRepresentation cancelOrder(HttpServletRequest request) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		try {
			Integer orderId = Integer.parseInt(request.getParameter("orderId"));
			if(orderActivity.validateOrder(orderId) == false)
				throw new OrderNotFoundException(orderId);
			orderRepresentation = orderActivity.cancelOrder(orderId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return orderRepresentation;
	}
	
	@RequestMapping(value="/order/checkOrderStatus", method=RequestMethod.GET, params="orderId")
	public @ResponseBody OrderRepresentation checkOrderStatus(HttpServletRequest request) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		try {
			Integer orderId = Integer.parseInt(request.getParameter("orderId"));
			if(orderActivity.validateOrder(orderId) == false)
				throw new OrderNotFoundException(orderId);
			orderRepresentation = orderActivity.checkOrderStatus(orderId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return orderRepresentation; 
	}

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6919160978660692882L;

	public CustomerNotFoundException(Integer customerId) {
		super("Could not find customer- " + customerId);
	}
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class OrderNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4064641490641085268L;

	public OrderNotFoundException(Integer orderId) {
		super("Could not find order - " + orderId);
	}
}


