package com.project.ws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Order;
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

	@RequestMapping(value="/order/findAll/{customerId}", method=RequestMethod.GET, produces={"text/html", "application/json","application/xml"})
	public @ResponseBody List<OrderRepresentation> findAllOrders(@PathVariable("customerId") Integer customerId) {
		List<OrderRepresentation> orderRepr = orderActivity.allOrders(customerId);
		return orderRepr;
    }

//	@RequestMapping(value="/order/findAll/activeOrders/{customerId}", method=RequestMethod.GET, produces={"application/json", "application/xml"})
//    public @ResponseBody List<OrderRepresentation> findAllActiveOrders(@PathVariable("customerId") Integer customerId) {
//		return orderActivity.findAllActiveOrders(customerId);
//    }
	
	@RequestMapping(value="/order/createOrder", method=RequestMethod.POST, produces={"application/json", "application/xml"})
	public @ResponseBody List<CartRepresentation> createOrder(@RequestBody Cart cart) {
		return productActivity.buyProduct(cart);
	}

	@RequestMapping(value="/order/placeOrder", method=RequestMethod.POST, produces={"application/json", "application/xml"})
	 public @ResponseBody OrderRepresentation placeOrder(@RequestBody OrderRequest orderRequest) {
		return orderActivity.placeOrder(orderRequest.getCustomerId());
	}
	
	@RequestMapping(value="/order/cancelOrder/{orderId}", method=RequestMethod.PUT, produces={"application/json", "application/xml"})
	 public @ResponseBody OrderRepresentation cancelOrder(@PathVariable("orderId") Integer orderId) {
		return orderActivity.cancelOrder(orderId);
	}
	
	@RequestMapping(value="/order/checkOrderStatus/{orderId}", method=RequestMethod.GET, produces={"application/json", "application/xml"})
	public @ResponseBody OrderRepresentation checkOrderStatus(@PathVariable("orderId") Integer orderId) {
		System.out.println("checking order for " + orderId);
		return orderActivity.checkOrderStatus(orderId);
	}

}
