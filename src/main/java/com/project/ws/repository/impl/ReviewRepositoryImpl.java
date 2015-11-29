package com.project.ws.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.project.ws.domain.Review;
import com.project.ws.repository.custom.ReviewCustomRepository;

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

	@Override
	@Transactional
	public Integer addReview(Review review) {
		String SQL = "";
		
		if(review.getReviewType().equals("product"))
			SQL = "INSERT INTO review (review_desc, review_type, cust_id, product_id,vendor_id, rating) VALUES ('" + 
				review.getReviewDesc() + "', '" + "P" + "', " + review.getCustId() + ", " + 
				review.getProductId() +", " + review.getVendorId() + ", " + review.getRating() + ")";
		else if(review.getReviewType().equals("vendor"))
			SQL = "INSERT INTO review (review_desc, review_type, cust_id, product_id, vendor_id, rating) VALUES ('" + 
					review.getReviewDesc() + "', '" + "V" + "', " + review.getCustId() 
					+ ", " + review.getProductId() + ", " + review.getVendorId() + ", " + review.getRating() +")";
		
		Query query = em.createNativeQuery(SQL);
		Integer count = query.executeUpdate();
		if (count == 1) 
			System.out.println("review added successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Double getAvgRatingProduct(Integer productId) {
		Double avgRating = 0.0;
		String SQL = "SELECT avg(rating) FROM Review WHERE productId = " + productId;
		avgRating = em.createQuery(SQL, Double.class).getSingleResult();
		return avgRating;
	}
	
	@Override
	@Transactional
	public Double getAvgRatingVendor(Integer vendorId) {
		Double avgRating = 0.0;
		String SQL = "SELECT avg(rating) FROM Review WHERE vendorId = " + vendorId;
		avgRating = em.createQuery(SQL, Double.class).getSingleResult();
		return avgRating;
	}

	@Override
	@Transactional
	public Integer deleteReview(Integer reviewId) {
		String SQL = "DELETE from review where review_id = " + reviewId ;
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("DELETE successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}

}
