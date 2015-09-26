
package com.project.ws.database.repository.custom;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.project.ws.database.domain.Review;


public interface ReviewRepository extends CrudRepository<Review, Integer>, ReviewCustomRepository {

	public List<Review> findByReviewId(Integer reviewId);

}


