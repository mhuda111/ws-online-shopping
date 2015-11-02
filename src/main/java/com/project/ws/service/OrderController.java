package com.project.ws.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Cart;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.OrderRequest;
import com.project.ws.workflow.OrderActivity;
import com.project.ws.workflow.ProductActivity;

@RestController
public class OrderController {

	@Autowired
    private OrderActivity orderActivity;
	
	@Autowired
	private ProductActivity productActivity;

//	@RequestMapping(value="/order", method=RequestMethod.GET, params="customerId")
//	public @ResponseBody List<OrderRepresentation> findAllOrders(HttpServletRequest request) {
//		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
//		List<OrderRepresentation> orderRepr = orderActivity.allOrders(customerId, "all");
//		return orderRepr;
//    }

	@RequestMapping(value="/order", method=RequestMethod.GET, params="customerId")
	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody List<OrderRepresentation> findAllOrders(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		List<OrderRepresentation> orderRepr = orderActivity.allOrders(customerId, "all");
		if(orderRepr == null)
			throw new ResourceNotFoundException();
		return orderRepr;
    }
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ex.getMessage();
    }
	
	@RequestMapping(value="/order/activeOrders", method=RequestMethod.GET, params="customerId")
    public @ResponseBody List<OrderRepresentation> findAllActiveOrders(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		return orderActivity.allOrders(customerId, "active");
    }
	
	@RequestMapping(value="/order/createOrder", method=RequestMethod.POST)
	public @ResponseBody List<CartRepresentation> createOrder(@RequestBody Cart cart) {
		return productActivity.buyProduct(cart);
	}

	@RequestMapping(value="/order/placeOrder", method=RequestMethod.PUT)
	 public @ResponseBody OrderRepresentation placeOrder(@RequestBody OrderRequest orderRequest) {
		return orderActivity.placeOrder(orderRequest.getCustomerId());
	}
	
	@RequestMapping(value="/order/cancelOrder", method=RequestMethod.PUT, params="orderId")
	 public @ResponseBody OrderRepresentation cancelOrder(HttpServletRequest request) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		return orderActivity.cancelOrder(orderId);
	}
	
	@RequestMapping(value="/order/checkOrderStatus", method=RequestMethod.GET, params="orderId")
	public @ResponseBody OrderRepresentation checkOrderStatus(HttpServletRequest request) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		return orderActivity.checkOrderStatus(orderId);
	}
	


}
