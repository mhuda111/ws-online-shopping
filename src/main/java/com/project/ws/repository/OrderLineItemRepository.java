package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.OrderLineItem;
import com.project.ws.repository.custom.OrderLineItemCustomRepository;

@Repository
public interface OrderLineItemRepository extends CrudRepository<OrderLineItem, Integer>, OrderLineItemCustomRepository {

	public List<OrderLineItem> findAll();
	public List<OrderLineItem> findByOrderId(Integer orderId);
	
}
