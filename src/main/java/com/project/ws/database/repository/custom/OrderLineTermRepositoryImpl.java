package com.project.ws.database.repository.custom;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.project.ws.database.domain.OrderLineItem;

/**
 * This is the implementation class of the customer custom repository interface.
 */
public class OrderLineTermRepositoryImpl implements OrderLineTermCustomRepository {

	/**
	 * This EntityManager attribute is used to create the database queries
	 */
	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Doing queries from database and mapped the results to the list of customer object.
	 */
	@Override
	public List<OrderLineItem> getOrderLineByOrderLineID(Integer orderLineId) {
		String SQL = "select o from OrderLineItem o where order_line_id = " + orderLineId;
		TypedQuery<OrderLineItem> query = em.createQuery(SQL, OrderLineItem.class);
		List<OrderLineItem> resultList = query.getResultList();
		return resultList;
	}

}
