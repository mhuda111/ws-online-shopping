package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.database.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>, OrderCustomRepository {
	
	public List<Order> findAll();
	public Order findOne(Integer orderId);
}
