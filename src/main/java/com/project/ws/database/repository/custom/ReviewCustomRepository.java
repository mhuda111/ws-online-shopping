
package com.project.ws.database.repository.custom;

import com.project.ws.database.domain.Review;

public interface ReviewCustomRepository {

	public Integer addReview(Review review);
	public Double getAvgRatingProduct(Integer productId);
	public Double getAvgRatingVendor(Integer vendorId);

}

