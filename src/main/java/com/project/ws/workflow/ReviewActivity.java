
package com.project.ws.workflow;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.ws.domain.Review;
import com.project.ws.workflow.custom.ReviewCustomActivity;


public interface ReviewActivity extends CrudRepository<Review, Integer>, ReviewCustomActivity {

	public List<Review> findByReviewId(Integer reviewId);
	public List<Review> findByVendorId(Integer vendorId);
	public List<Review> findByProductId(Integer productId);

}


