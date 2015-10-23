package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Order;
import com.project.ws.workflow.custom.OrderCustomRepository;

public interface OrderRepository extends CrudRepository<Order, Integer>, OrderCustomRepository {

	@Override
	public List<Order> findAll();
	@Override
	public Order findOne(Integer orderId);
}
