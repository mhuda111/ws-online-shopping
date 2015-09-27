package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.OrderLineItem;
import com.project.ws.database.repository.custom.OrderLineTermRepository;

/**
 * This is customer spring controller which has methods
 * that specify the end points for the customer web service.
 */
@RestController
public class OrderLineTermController {

	@Autowired
    private OrderLineTermRepository orderLineTermRepository;
	@RequestMapping("/OrderLineItem/")
    public List<OrderLineItem> getOrderLineByOrderLineID(HttpServletRequest request) {
		Integer orderLineId = Integer.parseInt(request.getParameter("orderLineId"));
    	return orderLineTermRepository.getOrderLineByOrderLineID(orderLineId);
    }

}
