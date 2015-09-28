package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.Order;
import com.project.ws.database.repository.order.OrderRepository;

/**
 * This is customer spring controller which has methods
 * that specify the end points for the customer web service.
 */
@RestController
public class OrderController {


	@Autowired
    private OrderRepository orderRepository;


	@RequestMapping("/order/findAll")
    public List<Order> findAllOrders(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		return orderRepository.findAllOrders(customerId);
    }


	@RequestMapping("/order/findAll/activeOrders")
    public List<Order> findAllActiveOrders(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		return orderRepository.findAllActiveOrders(customerId);
    }

	@RequestMapping("/order/addshipOrder")
	 public String shipOrder(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		int orderId = Integer.parseInt(request.getParameter("orderId"));


		int orderShipped = orderRepository.shipOrder(customerId, orderId);
		if (orderShipped > 0) {
			return "Successfully shipped the order ID " + orderId;
		}
		else return "Error";
	}


	@RequestMapping("/order/placeOrder")
	 public String placeOrder(HttpServletRequest request) {
		int customerId = Integer.parseInt(request.getParameter("customerId"));
		int billId = Integer.parseInt(request.getParameter("billId"));


		int orderPlaced = orderRepository.placeOrder(customerId, billId);
		if (orderPlaced > 0) {
			return "Successfully placed order the Bill ID " + billId;
		}
		else return "Error";
	}

	@RequestMapping("/order/orderCancel")
	 public String cancelOrder(HttpServletRequest request) {
		int orderId = Integer.parseInt(request.getParameter("orderId"));

		int orderCanceled = orderRepository.cancelOrder(orderId);
		if (orderCanceled > 0) {
			return "Successfully cancel order the Order ID " + orderId;
		}
		else return "Error";
	}




}
