package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Order;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.workflow.custom.OrderCustomActivity;

public interface OrderActivity extends CrudRepository<Order, Integer>, OrderCustomActivity {

	@Override
	public List<Order> findAll();
	@Override
	public Order findOne(Integer orderId);
}
