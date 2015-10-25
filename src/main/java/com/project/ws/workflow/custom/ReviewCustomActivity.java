
package com.project.ws.workflow.custom;

import com.project.ws.domain.Review;

public interface ReviewCustomActivity {

	public Integer addReview(Review review);
	public Double getAvgRatingProduct(Integer productId);
	public Double getAvgRatingVendor(Integer vendorId);

}

