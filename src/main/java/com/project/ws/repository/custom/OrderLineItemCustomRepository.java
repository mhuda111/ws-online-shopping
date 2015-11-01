package com.project.ws.repository.custom;

import java.util.List;

import com.project.ws.domain.OrderLineItem;

public interface OrderLineItemCustomRepository {

	public List<OrderLineItem> addOrderLine(Integer customerId, Integer orderId);
	
}
