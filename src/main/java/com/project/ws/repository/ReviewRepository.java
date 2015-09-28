
package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Review;


public interface ReviewRepository extends CrudRepository<Review, Integer>, ReviewCustomRepository {

	public List<Review> findByReviewId(Integer reviewId);
	public List<Review> findByVendorId(Integer vendorId);
	public List<Review> findByProductId(Integer productId);

}


