
package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.OrderLineItem;

public interface OrderLineTermCustomRepository {

	public List<OrderLineItem> getOrderLineByOrderLineID(Integer orderLineId);

}

