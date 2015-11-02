
package com.project.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Review;
import com.project.ws.repository.custom.ReviewCustomRepository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer>, ReviewCustomRepository {

	public List<Review> findByReviewId(Integer reviewId);
	public List<Review> findByVendorId(Integer vendorId);
	public List<Review> findByProductId(Integer productId);

}


