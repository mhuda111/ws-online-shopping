
package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.database.domain.OrderLineItem;


public interface OrderLineTermRepository extends CrudRepository<OrderLineItem, Integer>, OrderLineTermCustomRepository {

	public List<OrderLineItem> findByOrderLineId(Integer orderLineId);

}


