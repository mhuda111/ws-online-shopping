package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Order;
import com.project.ws.repository.custom.OrderCustomRepository;
import com.project.ws.representation.OrderRepresentation;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>, OrderCustomRepository {

	public List<Order> findAll();
	public Order findOne(Integer orderId);
}
