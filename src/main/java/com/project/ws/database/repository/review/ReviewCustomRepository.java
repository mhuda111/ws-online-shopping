
package com.project.ws.database.repository.review;

import com.project.ws.database.domain.Review;

public interface ReviewCustomRepository {

	public Integer addReview(Review review);
	public Double getAvgRatingProduct(Integer productId);
	public Double getAvgRatingVendor(Integer vendorId);

}

