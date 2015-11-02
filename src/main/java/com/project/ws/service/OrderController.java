package com.project.ws.service;

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
public class OrderController implements ErrorController {

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
	
	@Autowired
    private OrderActivity orderActivity;
	
	@Autowired
	private ProductActivity productActivity;
	
	@Autowired
	private CustomerActivity customerActivity;
	
    @Override
    public String getErrorPath() {
        return ERRORPATH;
    }
    
	@RequestMapping(ERRORPATH)
	public @ResponseBody String error(HttpServletRequest request, HttpServletResponse response) {
		return errorString + errorMessages.get(response.getStatus());
    }

	@RequestMapping(value="/order", method=RequestMethod.GET, params="customerId")
	public @ResponseBody List<OrderRepresentation> findAllOrders(HttpServletRequest request) {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		try {
			customerActivity.getCustomerById(customerId);
		} catch(RuntimeException ex) {
			throw new ResourceNotFoundException();
		}
		
		List<OrderRepresentation> orderRepr = orderActivity.allOrders(customerId, "all");
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


