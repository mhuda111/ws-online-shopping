
package com.project.ws.database.repository.custom;

import java.util.List;

import com.project.ws.database.domain.Review;

public interface ReviewCustomRepository {

	public List<Review> getReviewByReviewID(Integer reviewId);

}

