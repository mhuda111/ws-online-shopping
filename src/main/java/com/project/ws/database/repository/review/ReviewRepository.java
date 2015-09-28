
package com.project.ws.database.repository.review;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.project.ws.database.domain.Review;


public interface ReviewRepository extends CrudRepository<Review, Integer>, ReviewCustomRepository {

	public List<Review> findByReviewId(Integer reviewId);
	public List<Review> findByVendorId(Integer vendorId);
	public List<Review> findByProductId(Integer productId);

}


