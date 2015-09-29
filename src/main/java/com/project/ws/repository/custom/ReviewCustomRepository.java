
package com.project.ws.repository.custom;

import com.project.ws.domain.Review;

public interface ReviewCustomRepository {

	public Integer addReview(Review review);
	public Double getAvgRatingProduct(Integer productId);
	public Double getAvgRatingVendor(Integer vendorId);

}

