package com.project.ws.database.repository.custom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.project.ws.business.domain.CustomerBO;
import com.project.ws.database.domain.Customer;
import com.project.ws.database.domain.CustomerAddress;
import com.project.ws.database.domain.Review;

/**
 * This is the implementation class of the customer custom repository interface.
 */
public class ReviewRepositoryImpl implements ReviewCustomRepository {

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
	public List<Review> getReviewByReviewID(Integer reviewID) {
		String SQL = "select r from Review r where review_id = " + reviewID;
		TypedQuery<Review> query = em.createQuery(SQL, Review.class);
		List<Review> resultList = query.getResultList();
		return resultList;
	}

}
